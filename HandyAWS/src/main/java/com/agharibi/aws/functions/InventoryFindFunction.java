package com.agharibi.aws.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InventoryFindFunction implements RequestHandler<String, String> {

    @Override
    public String handleRequest(String input, Context context) {
        context.getLogger().log("Input: " + input);

        Region region = Region.US_EAST_2;
        S3Client s3Client = S3Client.builder().region(region).build();
        
        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket("handy-inventory-data-1201")
                .key("s3testdata.txt")
                .build();

        ResponseInputStream<?> objectData = s3Client.getObject(objectRequest);
        InputStreamReader inputStreamReader = new InputStreamReader(objectData);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String outputString = null;
        try {
            outputString = bufferedReader.readLine();
            bufferedReader.close();
        } catch (IOException e) {
            context.getLogger().log("An exceptoin was generated when attempting to readLine() bufferedReader");
        }

        return outputString;
    }

}
