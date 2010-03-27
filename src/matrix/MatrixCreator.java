package matrix;

import java.awt.Color;
import java.util.ArrayList;

import objectPos.Object;
import objectPos.Position;
import filterData.DataContainer;

public class MatrixCreator {

	private static final int MAX_BLUE = 240;
	private static final int MIN_BLUE = 10;

	private int height = 2800;
	private int width = 2800;
	private int defaultColor = 100;
	private int red;
	private int green;
	private int blue;
	private ArrayList<ArrayList<Color>> matrix;
	private ArrayList<int[]> data;
	private DataContainer infos;
	private double alpha;
	private int wheelwidth = 28;

	public MatrixCreator() {
		matrix = new ArrayList<ArrayList<Color>>();
	}
	
	/**
	 * 
	 */
	public void createMatrix() {
		for (int i = 0; i < width; i++) {
			matrix.add(new ArrayList<Color>());
			for (int j = 0; j < height; j++) {
				matrix.get(i).add(new Color(defaultColor, defaultColor, defaultColor));
			}
		}
	}

	/**
	 * 
	 */
	public void resetMatrix() {
		matrix.clear();
		createMatrix();
	}

	/**
	 * 
	 */
	public boolean checkMatrixSize(int x, int y) {
		if (x >= 0 && x < width && y >= 0 && y < height) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 */
	public void setPixelinMatrix(int x, int y, int r, int g, int b) {
		if (checkMatrixSize(x, y)) {
			matrix.get(x).set(y, new Color(r, g, b));
		} else {
			;// System.out.println("Error setPixelinMatrix(int x, int y, int r, int g, int b): out of map");
		}
	}

	/**
	 * 
	 */
	public void setPixelinMatrix(int x, int y, Color color) {
		if (checkMatrixSize(x, y)) {
			matrix.get(x).set(y, color);
		} else {
			System.out.println("Error setPixelinMatrix(int x, int y, Color color): out of map");
		}
	}

	/**
	 * 
	 */
	public void setNewPosition(Object obj) {
		if (obj.getOldPosition() == null) {
			if (checkMatrixSize(obj.getPosition().getX(), obj.getPosition().getY())) {
				obj.setCurrentColorObject(matrix.get(obj.getPosition().getX()).get(obj.getPosition().getY()));
				setPixelinMatrix(obj.getPosition().getX(), obj.getPosition().getY(), 0, 0, obj.getColor());
				obj.setOldPosition(obj.getPosition());
			}
		} else if (obj.getOldPosition().getX() == obj.getPosition().getX() && obj.getOldPosition().getY() == obj.getPosition().getY()) {
			System.out.println("object do not move");

		} else {
			// with delete old pos
			if (checkMatrixSize(obj.getPosition().getX(), obj.getPosition().getY())) {
				setPixelinMatrix(obj.getOldPosition().getX(), obj.getOldPosition().getY(), obj.getCurrentColorObject());
				obj.setCurrentColorObject(matrix.get(obj.getPosition().getX()).get(obj.getPosition().getY()));
				setPixelinMatrix(obj.getPosition().getX(), obj.getPosition().getY(), 0, 0, obj.getColor());
				obj.setOldPosition(obj.getPosition());
			}
		}
	}

	/**
	 * 
	 */
	public void setColorFromScannedPixel(int x, int y) {
		if (checkMatrixSize(x, y)) {
			blue = matrix.get(x).get(y).getBlue();
			green = matrix.get(x).get(y).getGreen();
			red = matrix.get(x).get(y).getRed();
		} else {
			System.out.println("Error setColorFromScannedPixel(int x, int y): out of map");
		}
	}

	/**
	 * 
	 */
	public void setScanInfos(DataContainer infos) {
		this.infos = infos;
		this.data = infos.getData();
	}

	/**
	 * 
	 */
	public ArrayList<ArrayList<Color>> getCreatedMatrix() {
		return matrix;
	}

	/**
	 * 
	 */
	private void drawLine(Position myPos, Position scanPos) {
		int x, y, error, differenz, schritt, dx, dy, inc_x, inc_y;
		x = myPos.getX();
		y = myPos.getY();
		dx = scanPos.getX() - x;
		dy = scanPos.getY() - y;
		if (dx > 0) {
			inc_x = 1;
		} else {
			inc_x = -1;
		}
		if (dy > 0) {
			inc_y = 1;
		} else {
			inc_y = -1;
		}
		if (Math.abs(dy) < Math.abs(dx)) {
			error = -Math.abs(dx);
			differenz = 2 * Math.abs(dy);
			schritt = 2 * error;
			while (x != scanPos.getX()) {
				if (x != myPos.getX()) {
					decPix(x, y);
				}
				x += inc_x;
				error = error + differenz;
				if (error > 0) {
					y += inc_y;
					error += schritt;
				}
			}
		} else {
			error = -Math.abs(dy);
			differenz = 2 * Math.abs(dx);
			schritt = 2 * error;
			while (y != scanPos.getY()) {
				if (y != myPos.getY()) {
					decPix(x, y);
				}
				y += inc_y;
				error = error + differenz;
				if (error > 0) {
					x += inc_x;
					error += schritt;
				}
			}
		}
		if ((x != scanPos.getX() && y != scanPos.getY())) {
			incPix(x, y);
		}
	}

	/**
	 * 
	 */
	public void decPix(int x, int y) {
		if (checkMatrixSize(x, y)) {
			if (matrix.get(x).get(y).getBlue() > MIN_BLUE && matrix.get(x).get(y).getGreen() > MIN_BLUE) {
				setPixelinMatrix(x, y, (matrix.get(x).get(y).getBlue() - 10), (matrix.get(x).get(y).getGreen() - 10), (matrix.get(x).get(y).getBlue() - 10));
			}
		}
	}

	/**
	 * 
	 */
	public void incPix(int x, int y) {
		if (checkMatrixSize(x, y)) {
			if (matrix.get(x).get(y).getBlue() < MAX_BLUE && matrix.get(x).get(y).getGreen() > MIN_BLUE) {
				setPixelinMatrix(x, y, (matrix.get(x).get(y).getBlue() + 10), (matrix.get(x).get(y).getGreen() + 10), (matrix.get(x).get(y).getBlue() + 10));
			}
		}
	}

	/**
	 * 
	 */
	public void updateMatrix() {
		Position myPos = new Position();
		myPos.setPosArray(infos.getPos());
		for (int i = 0; i < data.size(); i++) {
			int x = data.get(i)[0];
			int y = data.get(i)[1];
			setColorFromScannedPixel(x, y);
			if (blue < MAX_BLUE) {
				blue += 10;
				green += 10;
				red += 10;
			}
			setPixelinMatrix(x, y, red, green, blue);
			Position zielPos = new Position();
			zielPos.setPosArray(data.get(i));
			drawLine(myPos, zielPos);
		}
		data.clear();
	}

	/**
	 * 
	 */
	public void updateAngleToX() {
		double lchain = infos.getDirection()[0];
		double rchain = infos.getDirection()[1];
		if (Math.abs(lchain - rchain) > 1) {
			alpha += ((lchain - rchain) / wheelwidth) * 180 / Math.PI;
			if (alpha > 360) {
				alpha -= 360;
			}
			if (alpha < 0) {
				alpha += 360;
			}
		}
	}

	/**
	 * 
	 */
	public void updateDataTuple() {
		for (int i = 0; i < data.size(); i++) {
			int[] tuple = rotateVektor(alpha, data.get(i)[0], data.get(i)[1]);
			tuple[0] += infos.getPos()[0];
			tuple[1] += infos.getPos()[1];
			data.set(i, tuple);
		}
	}

	/**
	 * 
	 */
	private int[] rotateVektor(double alpha, int x, int y) {
		double newX = (x * Math.cos(Math.toRadians(alpha))) - y * Math.sin(Math.toRadians(alpha));
		double newY = x * Math.sin(Math.toRadians(alpha)) + y * Math.cos(Math.toRadians(alpha));
		int[] newTupel = { (int) newX, (int) newY };
		return newTupel;
	}

}