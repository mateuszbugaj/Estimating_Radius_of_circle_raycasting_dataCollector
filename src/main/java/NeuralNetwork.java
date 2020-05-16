import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.io.ClassPathResource;

import java.io.IOException;
import java.util.Arrays;

public class NeuralNetwork {
    MultiLayerNetwork model;

    public NeuralNetwork(String modelName){
        try {
            String modelPatch = new ClassPathResource(modelName).getFile().getPath();
            model = KerasModelImport.importKerasSequentialModelAndWeights(modelPatch);
        } catch (IOException | UnsupportedKerasConfigurationException | InvalidKerasConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param input ray-casting data
     * @return polar coordinates and diameter
     *  perform prediction for normalized ray-casting data and format output
     */
    public double[] predict(float[] input){
        // for convo Nd4j.create(input, new int[]{1, 1, 200})
        INDArray inputArray = Nd4j.create(input, new int[]{1, 200}).div(500); // todo: shape of inputArray shouldn't be hardcoded and depend on num of rays (data stored in model's name??)
        double[] output = model.output(inputArray).toDoubleVector();

        output[0] *= 100;
        output[1] *= 100;
        output[2] *= 100;
        return output;
    }


}
