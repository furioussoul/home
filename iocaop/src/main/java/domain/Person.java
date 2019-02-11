package domain;

public class Person {

	public String name;

	public String age;

	public Hands hands;

	public Feet feet;

	public void say(){
		System.out.println("my name is " + this.name
				+ " ,i'm " +  this.age +" years old,and "
				+ this.hands
				+ " ,and"
				+ this.feet);
	}
}
