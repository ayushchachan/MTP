# you can write to stdout for debugging purposes, e.g.
# print("this is a debug message")

# you can write to stdout for debugging purposes, e.g.
# print("this is a debug message")

def solution(H, X, Y):
    # write your code in Python 3.6
    H.sort();

    val = 0;
    i = 0
    while i < len(H) and val < X + Y:
        if val + H[i] > X + Y:
            break;
        val += H[i];
        i += 1;
    
    # we need to consider upto index [0, i) only
    # answer <= i;
    numCars = 0;
    j = i - 1;

    maxVal, minVal = max(X, Y), min(X, Y);
    while j >= 0:
        if H[j] <= maxVal:
            maxVal = maxVal - H[j];
            numCars += 1;
            maxVal, minVal = max(maxVal, minVal), min(maxVal, minVal);
        j -= 1;
    return numCars;








    
    
    


    
    
    
