import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ProgramTest extends Application {
			
	static String a;
	static String b;
	private static BorderPane root = new BorderPane();
	
	public static BorderPane getRoot()
	{
		return root;
	}
	
	@Override
	public void start(Stage primaryStage) {
		System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "1000");
		try {
			root.setCenter(FXMLLoader.load(getClass().getResource("/MainLayout.fxml")));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			    @Override public void handle(WindowEvent t) {
			    	System.exit(0);
			    }
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
	/*	try {
			//PrefixAdderGenerator pa = new KoggeStoneAdder();
			PrefixAdderGenerator pa = new LadnerFischerAdder();
			
			PrefixAdderSolver s = new PrefixAdderSolver(pa);
			//s.generateMesh(9, 12);
			a = JOptionPane.showInputDialog("Podaj pierwsz¹ liczbê w postaci binarnej:");
			b = JOptionPane.showInputDialog("Podaj drug¹ liczbê w postaci binarnej:");
			s.generateMesh(a, b);
			//s.generateMesh("1011111111111111111111111111111111111111111111111111111111111111100000000000000000000000000000000000000011111111111111111111111111111111111111111111111111010", "111111111111111111100000000000000000000011111111111111111111111111111000000000000000000011111111111111111111111111111111111111");
			
			s.startSolvingMesh();
			
			s.printSum();
			s.printMeshNodes();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		launch(args);
	}

}
