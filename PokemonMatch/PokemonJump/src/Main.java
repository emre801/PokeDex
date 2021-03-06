

import org.opencv.core.*;
import org.opencv.highgui.*;

public class Main {

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat image1 = Highgui.imread("C:/Users/John/Desktop/Metagross.png");
		Mat image2 = Highgui.imread("C:/Users/John/Desktop/nuzleaf.png");
		Mat result = new Mat();
		Core.compare(image1, image2, result, Core.CMP_NE);
		Mat m = new Mat();
		Core.extractChannel(result, m, 0);
		int core = Core.countNonZero(m);
		System.out.println(core);
	}
}