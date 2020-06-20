import unittest
from discrete_log import baby_giant
from discrete_log import pohlig_hellman
from discrete_log import rho

class DiscreteLogtests(unittest.TestCase):
    #2^777840 mod 4608347 == 3220595
    
    def test_baby_giant(self):
        metadata = baby_giant.solve(2, 3220595, 4608347)
        self.assertEqual(metadata.solution, 777840)
        self.assertGreater(metadata.secondsTaken, 0)

    def test_pohlig_hellman(self):
        metadata = pohlig_hellman.solve(2, 3220595, 4608347)
        self.assertEqual(metadata.solution, 777840)
        self.assertGreater(metadata.secondsTaken, 0)

    def test_rho(self):
        metadata = rho.solve(2, 3220595, 4608347)
        self.assertEqual(metadata.solution, 777840)
        self.assertGreater(metadata.secondsTaken, 0)

if __name__ == '__main__':
    unittest.main()
