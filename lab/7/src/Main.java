public final class Main {

    public static void main(String[] args) {
        final BankAccount bankAccount = new BankAccount("12345-678", 1000);

        // INLINE CLASS
//        final Thread t1 = new Thread() {
//            @Override
//            public void run () {
//                bankAccount.deposit(300);
//                bankAccount.withdraw(50);
//            }
//        };
//
//        final Thread t2 = new Thread() {
//            @Override
//            public void run() {
//                bankAccount.deposit(203.75);
//                bankAccount.withdraw(100);
//            }
//        };

        // WITH RUNNABLE CLASS
//        final Thread t1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                bankAccount.deposit(300);
//                bankAccount.withdraw(50);
//            }
//        });
//
//        final Thread t2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                bankAccount.deposit(203.75);
//                bankAccount.withdraw(100);
//            }
//        });

        // WITH LAMBDA FUNCTION
        final Thread t1 = new Thread(() -> {
            bankAccount.deposit(300);
            bankAccount.withdraw(50);
            System.out.println("Transaction completed for account " + bankAccount);
        });

        final Thread t2 = new Thread(() -> {
            bankAccount.deposit(203.75);
            bankAccount.withdraw(100);
            System.out.println("Transaction completed for account " + bankAccount);
        });

        t1.start();
        t2.start();
    }
}
