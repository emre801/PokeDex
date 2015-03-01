import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import com.musicg.fingerprint.FingerprintManager;
import com.musicg.fingerprint.FingerprintSimilarityComputer;
import com.musicg.wave.Wave;
import com.musicg.wave.extension.Spectrogram;


public class PokemonTest {

	public static String fileToLoad ="C:/Users/John/Desktop/pokemonCries/finger/fp.fingerprint";
	byte[] fp1;
	public static void main(String[] args){
		try {
			loadFingers();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static LinkedList<byte[]> pokemonSounds = new LinkedList<byte[]>();
	
	public static byte[] getFingerPrint(String file){
		FingerprintManager fingerprintManager=new FingerprintManager();
		byte[] loadedFp=fingerprintManager.getFingerprintFromFile(file);
		return loadedFp;
	}
	
	public static void compareFingerPrints(String file1, String file2){
		byte[] fp1 = getFingerPrint(file1);
		byte[] fp2 = getFingerPrint(file2);
		FingerprintSimilarityComputer fpSimComp = new FingerprintSimilarityComputer(fp1,fp2);
		System.out.println(fpSimComp.getFingerprintsSimilarity().getSimilarity());
		
		
	}
	
	public static void compareFingerPrints(byte[] b1, byte[] b2){
		FingerprintSimilarityComputer fpSimComp = new FingerprintSimilarityComputer(b1,b2);
		System.out.println(fpSimComp.getFingerprintsSimilarity().getSimilarity());
		
		
	}
	
	public PokemonTest(){
		fp1 = getFingerPrint(fileToLoad);
	}
	public  void compareFingerPrintStream(byte[] input){
		if(input==null)
			return;
		FingerprintSimilarityComputer fpSimComp = new FingerprintSimilarityComputer(fp1,input);
		System.out.println(fpSimComp.getFingerprintsSimilarity().getSimilarity());
	}
	
	public static void readSounds(){
		File folder = new File("C:/Users/John/Desktop/pokemonCries/WAV-651");
		for(File file: folder.listFiles()){
			//System.out.println(file.getPath());
			//saveFingerPrint(file.getName());
		}
	}
	public static void loadFingers() throws IOException{
		File folder = new File("C:/Users/John/Desktop/pokemonCries/finger");
		for(File file: folder.listFiles()){
			//pokemonSounds.add();
			byte[] temp = getFingerPrint(file.getPath());
			Complex[][] result = FFT(temp);
			System.out.println(result.length);
			writeFile("C:/Users/John/Desktop/pokemonCries/Bwaha/"+file.getName()+".txt",result);
		}
		//byte[] sound1 = getFingerPrint("C:/Users/John/Desktop/pokemonCries/fpTest3.fingerprint");
		//for(byte[] b:pokemonSounds){
		//	compareFingerPrints(sound1,b);
		//}
		
	}
	
	public static void writeFile(String fileName,Complex[][] result) throws IOException{

		//For every line of data:
		FileWriter fw = new FileWriter(fileName,false);
		for(int i =0;i<result.length;i++){
			double[] highscores = new double[5];
			int[] recordPoints = new int[5];
			for (int freq = Harvester.LOWER_LIMIT; freq < Harvester.UPPER_LIMIT-1; freq++) {
			    //Get the magnitude:
			    double mag = Math.log(result[i][freq].abs() + 1);
	
			    //Find out which range we are in:
			    int index = getIndex(freq);
	
			    //Save the highest magnitude and corresponding frequency:
			    if (mag > highscores[index]) {
			        highscores[index] = mag;
			        recordPoints[index] = freq;
			    }
			}
	
			//Write the points to a file:
			for (int k = 0; k < 5; i++) {
			    fw.append(recordPoints[i] + "\t");
			}
			fw.append("\n");

		}
		fw.close();

	}
	public static final int[] RANGE = new int[] {40,80,120,180, Harvester.UPPER_LIMIT+1};
	//Find out in which range
	public static int getIndex(int freq) {
	    int i = 0;
	    while(RANGE[i] < freq) i++;
	        return i;
	    }
	
	
	
	public static Complex[][] FFT(byte[] audio){

		final int totalSize = audio.length;
		
		int amountPossible = totalSize/Harvester.CHUNK_SIZE;
		System.out.println(totalSize + " " + amountPossible+ " amountPossible");
		
		//When turning into frequency domain we'll need complex numbers:
		Complex[][] results = new Complex[amountPossible][];
		
		//For all the chunks:
		for(int times = 0;times < amountPossible; times++) {
		    Complex[] complex = new Complex[Harvester.CHUNK_SIZE];
		    for(int i = 0;i < Harvester.CHUNK_SIZE;i++) {
		        //Put the time domain data into a complex number with imaginary part as 0:
		        complex[i] = new Complex(audio[(times*Harvester.CHUNK_SIZE)+i], 0);
		    }
		    //Perform FFT analysis on the chunk:
		    results[times] = FFT.fft(complex);
		}
		return results;
		
		//Done!

	}
	
	public static void saveFingerPrint( String fileName){
		String fullFileName = "C:/Users/John/Desktop/pokemonCries/WAV-651/"+fileName;
		String saveName = "C:/Users/John/Desktop/pokemonCries/finger/"+fileName+".fingerprint";
        // create a wave object
        Wave wave = new Wave(fullFileName);

        // get the fingerprint
        byte[] fingerprint=wave.getFingerprint();

        // dump the fingerprint
        FingerprintManager fingerprintManager=new FingerprintManager();
        fingerprintManager.saveFingerprintAsFile(fingerprint, saveName);
        
        //// load fingerprint from file
        //byte[] loadedFp=fingerprintManager.getFingerprintFromFile("C:/Users/John/Desktop/pokemonCries/finger/fp.fingerprint");
        
        /*
        // fingerprint bytes checking
        for (int i=0; i<fingerprint.length; i++){
                System.out.println(fingerprint[i]+" vs "+loadedFp[i]);
        }
        */
	}
	public static void printImage(){
		String filename = "C:/Users/John/Desktop/pokemonCries/WAV-651/001.wav";
		String outFolder = "C:/Users/John/Desktop/pokemonCries/image";

		// create a wave object
		Wave wave = new Wave(filename);
		Spectrogram spectrogram = new Spectrogram(wave);
		

		// Graphic render
		GraphicRender render = new GraphicRender();
		// render.setHorizontalMarker(1);
		// render.setVerticalMarker(1);
		render.renderSpectrogram(spectrogram, outFolder + "/spectrogram.jpg");

		// change the spectrogram representation
		int fftSampleSize = 512;
		int overlapFactor = 2;
		spectrogram = new Spectrogram(wave, fftSampleSize, overlapFactor);
		render.renderSpectrogram(spectrogram, outFolder + "/spectrogram2.jpg");

	}
}
