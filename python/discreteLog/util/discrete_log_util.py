def extended_euclid(num1,num2):
    #given values x,y return
    #(gcd(x,y),r,s) where r*x+s*y=gcd(x,y)
        (a,b,aa,bb)=(0,1,1,0)
        while num2 !=0:
            (q,r)=divmod(num1,num2)
            (a,b,aa,bb)=(aa-q*a,bb-q*b,a,b)
            (num1,num2)=(num2,r)
        return (num1,(aa,bb))


def chinese_remainder(z,factors):
    length1 = len(z)
    pairs = []
    for q in range(length1):
        pairs.append((z[q],factors[q]))
    (a, m)=pairs[0]
    for (b,p) in pairs[1:]:
        k=((b-a)*extended_euclid(m,p)[1][0]) % p
        a=(a+m*k) % (m*p)
        m = m * p
    return a


def prime_factors(n, startFrom=2):
    if n <= 1:  return [ ]
    d = startFrom
    factors = [ ]
    while n >= d*d:
      if n % d == 0:
        factors.append(d)
        n = n//d
      else:
        d += 1 + d % 2 
    factors.append(n)
    return factors

def count_consecutive_same(seq):
    #Given a sequence, return a list of (item, consecutive_repetitions)
    if not seq: return []
    current = NotImplemented
    n = 0
    pairs = []
    for e in seq:
        if e == current:
            n += 1
        else:
            if n > 0:
                pairs.append((current, n))
            n = 1
            current = e
    pairs.append((current, n))
    return pairs

def prime_factor_multiplicity(n):
    return count_consecutive_same(prime_factors(n))