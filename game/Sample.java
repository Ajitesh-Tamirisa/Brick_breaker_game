package game; 
import javax.swing.JFrame;
public class Sample {
	public static void main(String args[])
	{
		JFrame obj=new JFrame();
		Gameplay gPlay= new Gameplay();
		obj.setBounds(10, 10, 700, 600);
		obj.setTitle("Brick and Ball");
		obj.setResizable(true);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gPlay);
	}

}
//seperation of concerns  oodp princ