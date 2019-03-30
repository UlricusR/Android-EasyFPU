package info.rueth.fpucalculator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import info.rueth.fpucalculator.domain.model.AbsorptionBlock;
import info.rueth.fpucalculator.domain.model.AbsorptionScheme;
import info.rueth.fpucalculator.domain.model.FPU;
import info.rueth.fpucalculator.domain.model.Food;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class FoodUnitTest {
    @Test
    public void fpu_isCorrect() {
        Food food = new Food();
        food.setName("Chicken McNuggets");
        food.setCaloriesPer100g(249f);
        food.setCarbsPer100g(17f);
        food.setAmount(72);

        FPU fpu = food.getFPU();

        // Test FPUs
        assertEquals(1.303, fpu.getFPU(), 0.001);

        // Test absorption time
        List<AbsorptionBlock> absorptionBlocks = new ArrayList<>();
        absorptionBlocks.add(new AbsorptionBlock(1, 3));
        absorptionBlocks.add(new AbsorptionBlock(2, 4));
        absorptionBlocks.add(new AbsorptionBlock(3, 5));
        absorptionBlocks.add(new AbsorptionBlock(4, 6));
        absorptionBlocks.add(new AbsorptionBlock(6, 8));
        AbsorptionScheme absorptionScheme = new AbsorptionScheme(absorptionBlocks);
        assertEquals(3, fpu.getAbsorptionTime(absorptionScheme));
    }
}