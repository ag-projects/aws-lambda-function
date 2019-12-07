package com.agharibi.aws.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.Arrays;

public class InventoryFindFunction
    extends InventoryS3Client
    implements RequestHandler<HttpQueryStringRequest, HttpProductResponse> {

    @Override
    public HttpProductResponse handleRequest(HttpQueryStringRequest request, Context context) {
        context.getLogger().log("Input: " + request);

        String idAsString = request.getQueryStringParameters().get("id");

        if(idAsString.equalsIgnoreCase("all")) {
            Product[] products = getAllProducts();
            HttpProductResponse response = new HttpProductResponse(products);
            return response;
        }

        int productId = Integer.parseInt(idAsString);
        Product product = getProductById(productId);

        return new HttpProductResponse(product);
    }

	private Product getProductById(int prodId) {
        Product[] products = getAllProducts();

        return Arrays.stream(products)
            .filter(p -> p.getId() == prodId)
            .findAny()
            .orElse(null);
	}

}
