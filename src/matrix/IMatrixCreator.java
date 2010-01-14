package matrix;


/**
 * Interface der Klasse MatrixCreator
 */
import java.awt.Color;
import java.util.ArrayList;

public interface IMatrixCreator {
	public ArrayList<ArrayList<Color>> getCreatedMatrix();
	public void setPixelinMatrix(int x, int y, int r, int g, int b);

}
