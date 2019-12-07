package com.agharibi.aws.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class InventoryFindFunction implements RequestHandler<HttpQueryStringRequest, HttpProductResponse> {

    @Override
    public HttpProductResponse handleRequest(HttpQueryStringRequest request, Context context) {
        context.getLogger().log("Input: " + request);

        String idAsString = request.getQueryStringParameters().get("id");
        int productId = Integer.parseInt(idAsString);
        Product product = getProductById(productId);

        return new HttpProductResponse(product);
    }

	private Product getProductById(int prodId) {
		Region region = Region.US_EAST_2;
        S3Client s3Client = S3Client.builder().region(region).build();

        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket("handy-inventory-data-1201")
                .key("handy-tool-catalog.json")
                .build();

        ResponseInputStream<?> objectData = s3Client.getObject(objectRequest);
        InputStreamReader inputStreamReader = new InputStreamReader(objectData);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        Product[] products = null;
        Gson gson = new Gson();
        products = gson.fromJson(bufferedReader, Product[].class);

        return Arrays.stream(products).filter(p -> p.getId() == prodId).findAny().orElse(null);
	}

}
