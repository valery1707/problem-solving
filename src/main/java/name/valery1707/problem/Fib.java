package name.valery1707.problem;

public interface Fib {

    long fib(int n);

    enum Implementation implements Fib {
        classic {
            @Override
            public long fib(int n) {
                if (n < 2) {
                    return n;
                }
                return fib(n - 1) + fib(n - 2);
            }
        },
        tailRec {
            @Override
            public long fib(int n) {
                return tailRecFib(n, 0, 1);
            }

            private static int tailRecFib(int n, int a, int b) {
                if (n == 0) {
                    return a;
                }
                if (n == 1) {
                    return b;
                }
                return tailRecFib(n - 1, b, a + b);
            }
        },
    }

}
