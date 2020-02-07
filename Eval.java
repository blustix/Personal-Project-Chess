public class Eval implements Comparable <Eval>{
  private String xy;
  private int value;
  private String PI;
  
  public Eval(String xy, int value, String PI)
  {
    this.xy = xy;
    this.value = value;
    this.PI = PI;
  }
  public Eval()
  {

  }
  @Override
  public int compareTo(Eval x)
  {
    int comparedvalue = x.value;
    if(this.value > comparedvalue) return 1;
    else if (this.value == comparedvalue) return 0;
    else return -1;
  }

  public String xy()
  {
    return xy;
  }

  public int value()
  {
    return value;
  }
  public String PI()
  {
    return PI;
  }
}