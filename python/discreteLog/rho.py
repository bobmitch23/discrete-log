import time
from .util.discrete_log_util import extended_euclid
from .domain.discrete_log_metadata import DiscreteLogMetadata


def solve(gen, base,prime):
    start = time.time()
    sub = (prime - 1)//2
    X = x = gen*base
    A = a = 1
    B = b = 1

    for _ in range(1, prime-1):
        x, a, b = new_xab(x, a, b, gen, base, prime, sub)
        X, A, B = new_xab(X, A, B, gen, base, prime, sub)
        X, A, B = new_xab(X, A, B, gen, base, prime, sub)

        if x == X:
            break

    finalNum = (extended_euclid(B-b, sub)[1][0] * (a-A)) % sub
    
    if pow(gen, finalNum, prime) == base:
        return DiscreteLogMetadata(finalNum, time.time()-start)
    elif pow(gen, finalNum+sub, prime) == base:
        return DiscreteLogMetadata(finalNum+sub, time.time()-start)
    else:
        return DiscreteLogMetadata(None, time.time()-start)


def new_xab(x, a, b, gen, base, prime, sub):
    if (x%3 == 0):
        x = x*gen % prime
        a = (a+1) % sub
    elif (x%3 == 1):
        x = x * base % prime
        b = (b + 1) % sub
    else:
        x = x*x % prime
        a = a*2 % sub
        b = b*2 % sub
    return x, a, b
