import org.junit.Assert;
import org.junit.Test;

public class SolutionTests {

    @Test
    public void testEmptyStringReturnsZero() {
        Calc calc = new Calc();
        Assert.assertSame(calc.add(""), 0);
    }

    @Test
    public void testEmptyStringReturnsOne() {
        Calc calc = new Calc();
        Assert.assertSame(calc.add("1"), 1);
    }

    @Test
    public void testEmptyStringReturnsTwo() {
        Calc calc = new Calc();
        Assert.assertSame(calc.add("2"), 2);
    }

    @Test
    public void testSumWithComma() {
        Calc calc = new Calc();
        Assert.assertSame(calc.add("1,2"), 3);
        Assert.assertSame(calc.add("1,2,3"), 6);
    }

    @Test
    public void testSumWithCommaAndNewline() {
        Calc calc = new Calc();
        Assert.assertSame(calc.add("1,2\n3,1"), 7);
        Assert.assertSame(calc.add("1\n2,3,1"), 7);
    }

    @Test
    public void testSumWithCustomDelimiter() {
        Calc calc = new Calc();
        Assert.assertSame(calc.add("//;\n1;2;3;1"), 7);
    }

    @Test
    public void testSumWithCustomDelimiterLength() {
        Calc calc = new Calc();
        Assert.assertSame(calc.add("//[***]\n1***2***3"), 6);
    }

    @Test
    public void testSumWithCustomSetOfDelimiters() {
        Calc calc = new Calc();
        Assert.assertSame(calc.add("//[*][%]\n1*2%3"), 6);
    }

    @Test
    public void testSumWithCustomSetOfDelimitersLength() {
        Calc calc = new Calc();
        Assert.assertSame(calc.add("//[ab][cd]\n1ab2cd3ab5"), 11);
    }

    @Test
    public void testSumWithCustomSetOfDelimitersLengthBiggerThan1000() {
        Calc calc = new Calc();
        Assert.assertSame(calc.add("//[ab][cd]\n1000ab1cd1"), 2);
    }


    @Test
    public void testSumWithCustomSetOfDelimitersLengthBiggerThan2000() {
        Calc calc = new Calc();
        Assert.assertSame(calc.add("//[ab][cd]\n2000ab1cd1"), 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSumWithCustomSetOfDelimitersLengthNegative() {
        Calc calc = new Calc();
        calc.add("//[!@#][!@$]\n-1!@$10!@#20");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSumWithCustomSetOfDelimitersLengthNegatives() {
        Calc calc = new Calc();
        calc.add("//[!@#][!@$]\n-1!@$10!@#-20");
    }

}
