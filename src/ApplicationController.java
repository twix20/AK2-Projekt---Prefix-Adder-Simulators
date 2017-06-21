

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class ApplicationController implements Initializable {

	@FXML
	TextArea authors;
	@FXML
	BorderPane mainPane;
	@FXML
	BorderPane sidePane;
	@FXML
	Label title;
	@FXML
	Label computeTitle;
	@FXML
	Canvas canvas;
	@FXML
	TextField firstField;
	@FXML
	TextField secondField;
	@FXML
	TextField thirdField;
	@FXML
	TextArea tempTextField;
	
	int x, y;
	int shapeSize = 60;
	int spacing = 20;
	PrefixAdderSolver s = null; 
	boolean fired = false;
	
	static String textField1;
	static String textField2;
	static String titleToObtain = "Ready?";
	static PrefixAdderGenerator pa;
	static boolean solved = false;
	BorderPane temp;
	ArrayList<Position> elements = new ArrayList<Position>();
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		temp = ProgramTest.getRoot();
		if(authors != null)
		authors.setText("Autorzy: \n"
				+ "Piotr Markiewicz 226020 \n"
				+ "Bartosz Gardziejewski 226128 \n");
		if(computeTitle != null)
		{
			computeTitle.setText(titleToObtain);
		}
	}

	
	@FXML
	public void koggeStone(){
		titleToObtain = "Sumator Kogge'a-Stone'a";
		pa = new KoggeStoneAdder();
		try {
				temp.setCenter(null);
				temp.setCenter(FXMLLoader.load(getClass().getResource("/ComputingLayout.fxml")));
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(this.hashCode());
		}

	}
		
	@FXML
	public void ladnerFischer(){
		titleToObtain = "Sumator Ladner'a-Fischer'a";
		pa = new LadnerFischerAdder();
		try {
			temp.setCenter(null);
			temp.setCenter(FXMLLoader.load(getClass().getResource("/ComputingLayout.fxml")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}

	}
	
	@FXML
	public void summarize()
	{

		if(solved)
		{
			thirdField.setText(null);
			elements.clear();
			if(pa.getClass() == LadnerFischerAdder.class )
			{
				pa = null;
				pa = new LadnerFischerAdder();
			}
			else
			{
				pa = null;
				pa = new KoggeStoneAdder();
			}
		}
			
		s = null;
		s = new PrefixAdderSolver(pa);
		s.generateMesh(firstField.getText(), secondField.getText(), this);
		if(!fired)
		{
		Thread test = new Thread(new Runnable(){

			@Override
			public void run() {
				while(true)
				{
				drawMesh(s);
				drawLines(s);
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				//drawMesh(s);
				//drawLines(s);
				
			}});
		test.setDaemon(true);
		test.start();
		}
		Thread solving = new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					
					s.startSolvingMesh();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				thirdField.setText(s.printSum());
			}});

		solving.start();
		/*String temp = "";
		for(int i = 0; i < toCompute.size(); i++)
			temp = temp + toCompute.get(i) + "\n";
		tempTextField.setText(temp);*/
		solved = true;
		fired = true;
	}

	
	private void drawMesh(PrefixAdderSolver s)
	{
		elements.clear();
		canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		if( (s.getGenerator().length +2) * (shapeSize+spacing) > canvas.getWidth())
		{
			canvas.setWidth((s.getGenerator().length +2) * (shapeSize+spacing));
		}
		if((s.getGenerator().depth +3) * (shapeSize+spacing*2) > canvas.getHeight())
		{
			canvas.setHeight((s.getGenerator().depth + 3 ) * (shapeSize+spacing*2));
		}
		x = (shapeSize + spacing)*s.getGenerator().length; y = shapeSize;
		
		for(int i = 0; i< s.getGenerator().getMeshNodes().size();i++)
		{	Node n = s.getGenerator().getNodeByPosition(i);
			drawNode(n);
			elements.add(new Position(x,y,i, n.getPrevParent() == null ? -1 : n.getPrevParent().getPosition()));
			x=x-shapeSize - spacing;
			
			if(i % s.getGenerator().length == s.getGenerator().length - 1){
				y = y+shapeSize + spacing*2;
				x =  (shapeSize + spacing)*s.getGenerator().length;
			}

		}
	}
	
	private void drawLines(PrefixAdderSolver s)
	{
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setStroke(Color.BLACK);
		for(int i = 0; i< elements.size(); i++)
		{
			Position e1 = elements.get(i);
			for(int j = 0; j< elements.size(); j++)
			{
				Position e2 = elements.get(j);
				if(i != j &&  e1.getX() == e2.getX() && (e2.getIndex() - e1.getIndex()) == s.getGenerator().length )
				{
						gc.strokeLine(e1.getX()+shapeSize/2, e1.getY()+shapeSize, e2.getX()+shapeSize/2, e2.getY());
						if(e1.getParent() >-1)
						{
							Position temp = elements.get(e1.getParent());
							gc.strokeLine(e1.getX()+shapeSize/2, e1.getY(), temp.getX()+shapeSize/2, temp.getY()+shapeSize);
							//System.out.println("Po³¹czy³em: " + e1.getIndex() + " z " + e1.getParent());
						}
				}

			}
		}
	}
	
	public void fastRedraw()
	{
		drawMesh(s);
		drawLines(s);
	}
	
	private void drawNode(Node n)
	{
		String type = n.getNodeType();
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.web("#F0F0F0"));
		
		String generation, propagation, sum;
		gc.setStroke(Color.DARKGRAY);
		switch(type)
		{
		case "I"://initial
			if(n.getResult().status == Status.Computed)
				gc.setStroke(Color.RED);
			if(n.getResult().generation == null || n.getResult().generation == Bit.Zero)
				generation = "0";
			else
				generation = "1";
			
			if(n.getResult().propagation == null || n.getResult().propagation == Bit.Zero)
				propagation = "0";
			else
				propagation = "1";
			
			gc.fillText("P: "+propagation+" | " +"G: "+generation, x+(shapeSize/8), y+(shapeSize/2)+3);
			gc.strokeRoundRect(x, y, shapeSize, shapeSize, 5, 5);
			break;
		case "H"://hanging
			if(n.getResult().status == Status.Computed)
			gc.setStroke(Color.GREEN);
			
			if(n.getResult().generation == null || n.getResult().generation == Bit.Zero)
				generation = "0";
			else
				generation = "1";
			
			if(n.getResult().propagation == null || n.getResult().propagation == Bit.Zero)
				propagation = "0";
			else
				propagation = "1";
			
			gc.fillText("P: "+propagation+" | " +"G: "+generation, x+(shapeSize/8), y+(shapeSize/2)+3);
			gc.strokeOval(x, y, shapeSize, shapeSize);
			break;
		case "W": //worker
			if(n.getResult().status == Status.Computed)
			gc.setStroke(Color.YELLOW);
			
			if(n.getResult().generation == null || n.getResult().generation == Bit.Zero)
				generation = "0";
			else
				generation = "1";
			
			if(n.getResult().propagation == null || n.getResult().propagation == Bit.Zero)
				propagation = "0";
			else
				propagation = "1";
			
			gc.fillText("P: "+propagation+" | " +"G: "+generation, x+(shapeSize/8), y+(shapeSize/2)+3);
			gc.strokeOval(x, y, shapeSize, shapeSize);
			break;
		case "R"://result
			if(n.getResult().status == Status.Computed)
			gc.setStroke(Color.BLUE);
			if(n.getResult().generation == null || n.getResult().generation == Bit.Zero)
				generation = "0";
			else
				generation = "1";
			
			if(n.getResult().sum == null || n.getResult().sum == Bit.Zero)
				sum = "0";
			else
				sum = "1";
			
			gc.fillText("C: "+generation+" | " +"S: "+sum, x+(shapeSize/8), y+(shapeSize/2)+3);
			gc.strokeRoundRect(x, y, shapeSize, shapeSize, 5, 5);
			break;
		default:
			System.out.println("HEJ TO NIE TU!");
		}
	}
	
	@FXML
	public void back(){

		try {
			temp.setCenter(null);
			temp.setCenter(FXMLLoader.load(getClass().getResource("/MainLayout.fxml")));
			pa = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	@FXML
	public void exit(){
		System.exit(0);
	}
}
