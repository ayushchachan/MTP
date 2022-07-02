import numpy as np
import pandas as pd
import scipy.stats
from termcolor import colored
from IPython.display import display

class Analysis:
    def __init__(self, reactants, products, error_percent_orig, df2):
        self.reactants = reactants;
        self.products = products;
        self.error_percent_orig = error_percent_orig;

        self.all_compounds = reactants + products
        self.error_percent = [e for e in error_percent_orig if e is not None]

        self.coeff_is_measured = [True if e is not None else False for e in error_percent_orig]
        self.df2 = df2;
        self.col = df2.columns
        self.row = df2.index

    def string_process1(self, compound):
        i = 0
        j = 1

        e_list = []

        while j < len(compound):
            if compound[j].isupper():
                e_list.append(compound[i:j])
                i = j
            j = j + 1
        if i != j:
            e_list.append(compound[i:j])

        element_map = dict()
        for e in e_list:

            for i in range(len(e)):
                if e[i].isdigit():
                    key = e[0:i]
                    value = float(e[i:len(e)])
                    element_map[key] = value
                    break
            else:
                element_map[e] = 1

        return element_map

    def perform_analysis(self, confidence = 0.9):
        # -----------------------------------------------
        reactant_maps = []
        product_maps = []

        self.all_elements = set()  # all elements in the reaction for eg. C, H, Cl, K, N

        for r in self.reactants:
            e_map = self.string_process1(r)  # element map for reactant r
            reactant_maps.append(e_map)
            self.all_elements = self.all_elements.union(e_map.keys())

        for p in self.products:
            e_map = self.string_process1(p)  # element map for product p
            product_maps.append(e_map)
            self.all_elements = self.all_elements.union(e_map.keys())

        self.all_elements = sorted(self.all_elements)
        print("all element involved in chemical reaction are:", self.all_elements)

        reactant_tuples = []
        product_tuples = []

        for r_map in reactant_maps:
            r_tuple = []
            for k in self.all_elements:
                if k in r_map:
                    r_tuple.append(r_map[k])
                else:
                    r_tuple.append(0)
            reactant_tuples.append(tuple(r_tuple))

        for p_map in product_maps:
            p_tuple = []
            for k in self.all_elements:
                if k in p_map:
                    p_tuple.append(p_map[k])
                else:
                    p_tuple.append(0)
            product_tuples.append(tuple(p_tuple))

        for i in range(len(self.reactants)):
            print("reactant_i =", self.reactants[i], ", -->", reactant_tuples[i])

        for i in range(len(self.products)):
            print("product_i =", self.products[i], ", -->", product_tuples[i])

        reactant_series = []
        product_series = []

        for i in range(len(self.reactants)):
            series_react_i = pd.Series(reactant_tuples[i], self.all_elements)
            reactant_series.append(-1 * series_react_i)

        for i in range(len(self.products)):
            series_product_i = pd.Series(product_tuples[i], self.all_elements)
            product_series.append(series_product_i)

        self.all_compounds_series = reactant_series + product_series
        E_Orig = pd.DataFrame(dict(zip(self.all_compounds, self.all_compounds_series)))

        E = E_Orig
        display(E)
        print()
        A = np.array(E)
        print("A =", '\n')
        print(A, '\n')

        h_no_deletion = []
        h_val = {}

        for i in range(len(self.all_compounds)):
            if self.coeff_is_measured[i]:
                h_val[self.all_compounds[i]] = []
                

        for i in self.df2.index:
            all_coeff = []
            for z in self.df2.loc[i].values[2:]:
                if z is not None:
                    z = round(abs(z), 4)
                all_coeff.append(z)
            print("i = ", i)
            coeff_not_measured_index = []

            for j in range(len(all_coeff)):
                if all_coeff[j] == None:
                    coeff_not_measured_index.append(j)

            print("all_coeff = ", all_coeff)
            print("coeff not measured", coeff_not_measured_index)

            E1 = E_Orig.copy()
            E = self.eliminate_unmeasured_coeff(E1, all_coeff, coeff_not_measured_index)
            A = np.array(E)

            #------added today----
            print("A =", '\n')
            print(A, '\n')
            #------added today----
            
            x_measured = np.array([[i] for i in all_coeff if i is not None])
            print("x_measured = ", x_measured)
            test_passed, h_orig = self.perform_chi_square_test(E, x_measured,
                                                        self.error_percent, confidence)
            print("h_org =", h_orig)
            h_no_deletion.append(h_orig);
            
            # if test_passed:
            #     print("since h <= chi-square with", len(A), "degrees of freedom at",
            #         confidence * 100, "% confidence level, \n\
            # we can say that gross errors are not present in system.")
            # else:
            #     print("since h >> chi-square with", len(A), "degrees of freedom at",
            #         confidence * 100, "% confidence level, \n\
            # we can state with 90% of confidence that some of the measurements have gross error.")
            #     print("Hence we proceed with serial elimination algorithm to find the location of possible gross errors.\n")

            h_values_serial_elimination = self.perform_serial_elimination(E, confidence, all_coeff)

            print("-----------------------------")
            display(h_values_serial_elimination)
            print("-------------------------------")
            for k in h_values_serial_elimination:
                h_val[k].append(h_values_serial_elimination[k])



        analysis = pd.DataFrame()
        analysis["D"] = self.df2[self.col[0]]
        analysis["DW"] = self.df2[self.col[1]]

        analysis['h (no deletion)'] = np.round(h_no_deletion, 2)


        for i in range(len(self.all_compounds)):
            if self.coeff_is_measured[i]:
                analysis["h after deleting " + str(self.all_compounds[i])] = np.round(h_val[self.all_compounds[i]], 2)

        display(analysis)    

    def eliminate_unmeasured_coeff(self, E, all_coeff, coeff_not_measured_index):
        element_balance_used = set()
        for i in coeff_not_measured_index:
            s = self.all_compounds_series[i]  # series for compound i

            x = (None, float('inf'))
            for e in self.all_elements:
                if (s[e] not in element_balance_used) and abs(s[e]) > 0 and abs(
                        s[e]) < x[1]:
                    x = (e, abs(s[e]))
            # print("series =")
            # print(s, "\n")
            x = x[0]
            element_balance_used.add(x)
            #         print("We will use", x, "balance to derive the stiochiometric coefficient of " +
            #               all_compounds[i] + "\n")
            #         print("Performing row operations so that the stiochiometric coefficient of " + all_compounds[i] +
            #               " does not appear in other balances")

            other_elements = set(self.all_elements)
            other_elements.remove(x)
            # print("other elements =", other_elements)

            for e2 in other_elements:
                #             print("-------------- After doing row operation for ",
                #                   e2, "------------------", ' we get \n')
                #             print(" ")
                E.loc[e2] = E.loc[e2] - E.loc[x] * (s[e2] / s[x])
                #             display(E)
                #             print()
                A = np.array(E)
            


            # print("A =", '\n')
            # print(A, '\n')
            # print("*---------*-----------*----------*---------*---------*")

    #     print("Since coefficients for", [all_compounds[i] for i in coeff_not_measured_index], "occurs, respectively, \
    # in rows of ", element_balance_used, "only, \n We can drop rows", element_balance_used, " from matrix as well as corresponding\
    # columns.\n These deleted elemental balances will be used to find values of unmeasured Stoichiometric coefficients\n")

    #     print("--------deleting rows of ", element_balance_used, "we get ------------")
        E = E.drop(list(element_balance_used))
        display(E)
        #     print("*---------*-----------*----------*---------*---------*\n")

        #     print("--------deleting columns of ", [all_compounds[i] for i in coeff_not_measured_index], "we  get ------------")
        z = [self.all_compounds[i] for i in coeff_not_measured_index]
        E = E.drop(z, axis=1)
        display(E)
        #     print("*---------*-----------*----------*---------*---------*\n")
    
        return E


    def perform_chi_square_test(self, E, x_measured, error_percent, confidence):
        #     display(E)
        print()
        A = np.array(E)
        #     print("A =", '\n')
        #     print(A, '\n')

        #     print("Checking the consistency of data")
        #     print("Ax =", '\n')
        #     print(A @ x_measured, '\n')

        error_abs = np.array(error_percent) * 0.01
        #     print("error_abs =", '\n')
        #     print(error_abs, '\n')

        N = len(error_percent)
        psi = np.zeros((N, N))
        #     print(psi)

        for i in range(N):
            psi[i, i] = (x_measured[i][0] * error_abs[i])**2


    #     print("psi =", '\n')
    #     print(psi, '\n')

        epsilon = -A @ x_measured
        #     print("epsilon =", '\n')
        #     print(epsilon, '\n')

        phi = A @ psi @ np.transpose(A)
        #     print("phi =", '\n')
        #     print(phi, '\n')

        # test function

        h = np.transpose(epsilon) @ np.linalg.inv(phi) @ epsilon
        h = h[0][0]

        #     print("the value of test function, h, is compared with chi-square distribution with m \
        # degrees of freedom \nwhere m = no of rows in our matrix A i.e. number of balances we have \
        # incorporated to find gross measurement errors\n")

        #     print("Since currently matrix A has dimension",
        #           np.shape(A), ", we have m = ", len(A))
        #     print("we will be using a confindence value of", confidence*100, "%.\n")

        #     print("h = ", end="")
        #     print(h, '\n')

        chi_square_value = scipy.stats.chi2.ppf(confidence, df=len(A))
        print("We have chi-square with", len(A), "degrees of freedom at",
                confidence*100, "% confidence level = ", chi_square_value, "\n")

        if h - chi_square_value <= 0:
            print(
                colored(
                    "--------------------------------------------\n\
    --------------Test Passed-------------------\n\
    --------------------------------------------\n", "green"))
            return True, h
        else:
            print(
                colored(
                    "********************************************\n\
    *************Test Failed********************\n\
    ********************************************\n", "red"))
            return False, h


    def perform_serial_elimination(self, matrix_E, confidence, all_coeff):
        #     print("Performing Serial Elimination Over matrix E shown just below\n")
        # display(matrix_E)
        #     print("we will we deleting one coefficient at a time and then again repeat the statistical analysis\n")
        element_balance_left = list(matrix_E.index)

        test_passed = []
        h_values_after_deletion = {}
        for i in range(len(all_coeff)):
            if all_coeff[i] is not None:
                E = matrix_E.copy()
                print("deleting the measurement for", self.all_compounds[i])
                print(
                    "performing row operations and delting corresponding row and column\n"
                )
                s = E[self.all_compounds[i]]  ## series for compound i

                x = (None, float('inf'))
                for e in element_balance_left:
                    if abs(s[e]) > 0 and abs(s[e]) < x[1]:
                        x = (e, abs(s[e]))
                # print("series =")
                # print(s, "\n")
                x = x[0]

                # print("We will use", x, "balance to derive the stiochiometric coefficient of " + all_compounds[i] + "\n")
                other_elements = set(element_balance_left)
                other_elements.remove(x)
                # print("other elements =", other_elements)
                # print("Performing row operations so that the stiochiometric coefficient of " + all_compounds[i] + " does not appear in other balances\n")

                for e2 in other_elements:
                    # print("-------------- After doing row operation for ", e2, "------------------", ' we get \n')
                    # print(" ")
                    E.loc[e2] = E.loc[e2] - E.loc[x] * (s[e2] / s[x])
                    # display(E)
                    # print()
                    # A = np.array(E)
                    # print("A =", '\n')
                    # print(A, '\n')
                    # print("*---------*-----------*----------*---------*---------*")

                    #                 print("Since coefficients for", all_compounds[i], "occur in row of ", x, "only, \n We can drop row for ", x, \
                    # " from matrix as well as corresponding columns.\n")
                    # print("--------deleting rows of ", x, "we get ------------")
                    E = E.drop([x])
                    # display(E)

                    # print("--------deleting columns of ", all_compounds[i], "we  get ------------")
                    z = [self.all_compounds[i]]
                    E = E.drop(z, axis=1)
                    # display(E)

                    # print("*---------*-----------*----------*---------*---------*\n")

                # display(E)
                # print("*---------*-----------*----------*---------*---------*\n")


    #             print("we will now repeat the same procedure i.e. compute the value of test function\n\
    # and compare it chi-square distribution value\n")
                coeff_left = []
                error_percent2 = []
                for j in range(len(all_coeff)):
                    if j != i and all_coeff[j] is not None:
                        coeff_left.append(all_coeff[j])
                        error_percent2.append(self.error_percent_orig[j])

                x_measured2 = np.array([[c] for c in coeff_left])
                # print("x_measured ----> ", x_measured)

                result, h = self.perform_chi_square_test(E, x_measured2, error_percent2, confidence)
                h_values_after_deletion[self.all_compounds[i]] = h;
                if result:
                    test_passed.append(i)
        print(
            colored(
                "\nDeletion of stoichiometric coefficients of " +
                str([self.all_compounds[i]
                    for i in test_passed]) + " passes the chi-square test",
                'blue'))
        print(
            colored(
                "Hence " + str([self.all_compounds[i] for i in test_passed]) +
                " measurements can be suspected of containg GROSS ERRORS", 'blue'))
        return h_values_after_deletion;




