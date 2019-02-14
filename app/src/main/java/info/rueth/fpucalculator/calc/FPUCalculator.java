package info.rueth.fpucalculator.calc;

public class FPUCalculator {

    public static void main(String[] args) {
        // Create absorption scheme
        AbsorptionScheme absorptionScheme = new AbsorptionScheme();

        // Add absorption blocks - unsorted on purpose to test sorting!
        absorptionScheme.addBlock(2, 4);
        absorptionScheme.addBlock(3, 5);
        absorptionScheme.addBlock(6, 8);
        absorptionScheme.addBlock(1, 3);
        absorptionScheme.addBlock(4, 6);

        System.out.println(absorptionScheme.toString());

        // Create new food with calories from carbs being higher than total calories
        try {
            Food strangeFood = new Food("Strange Food", 100.5, 30.1);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("--> Everything fine, exception was intended!");
        }

        Food pizza;
        try {
            pizza = new Food("Pizza", 229, 24);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Uups! That shouldn't have happened!");
            return;
        }

        // Now let's eat 160 of Pizza, which should give 2.128 FPE and 21.28 g eCarbs
        // and an absorption time of 5 hours
        FPU pizzaFPU = pizza.getFPU(160, absorptionScheme);

        double fpus = pizzaFPU.getFPU();
        if (fpus != 2.128) {
            System.out.println("Error: getFPU: Expected 2.128, received " + fpus);
        } else {
            System.out.println("Fine! My pizza has " + fpus + "FPU");
        }

        double eCarbs = pizzaFPU.getExtendedCarbs();
        if (eCarbs != 21.28) {
            System.out.println("Error: getExtendedCarbs: Expected 21.28, received " + eCarbs);
        } else {
            System.out.println("Fine! My pizza has " + eCarbs + "g eCarbs");
        }

        double absorptionTime = pizzaFPU.getAbsorptionTime();
        if (absorptionTime != 5) {
            System.out.println("Error: getAbsorptionTime: Expected 5h, received " + absorptionTime);
        } else {
            System.out.println("Fine! My pizza will be absorbed in " + absorptionTime + "h");
        }

        // Now let's test the absorption scheme:
        // 0,8 FPE should give
    }

}
