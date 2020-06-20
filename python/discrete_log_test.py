import unittest
from discreteLog import baby_giant
from discreteLog import pohlig_hellman
from discreteLog import rho

class DiscreteLogtests(unittest.TestCase):
    #2^777840 mod 4608347 == 3220595
    
    def test_baby_giant(self):
        metadata = baby_giant.solve(2, 3220595, 4608347)
        self.assertEqual(metadata.solution, 777840)
        self.assertGreater(metadata.timeTaken, 0)

    def test_pohlig_hellman(self):
        metadata = pohlig_hellman.solve(2, 3220595, 4608347)
        self.assertEqual(metadata.solution, 777840)
        self.assertGreater(metadata.timeTaken, 0)

    def test_rho(self):
        metadata = rho.solve(2, 3220595, 4608347)
        self.assertEqual(metadata.solution, 777840)
        self.assertGreater(metadata.timeTaken, 0)

if __name__ == '__main__':
    unittest.main()
