package com.agharibi.aws.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.ArrayList;

public class InventoryDeleteFunction  extends InventoryS3Client
    implements RequestHandler<HttpRequest, HttpProductResponse> {

    @Override
    public HttpProductResponse handleRequest(HttpRequest request, Context context) {
        context.getLogger().log("Input: " + request);

        String idAsString = request.getPathParameters().get("id");
        int productId = Integer.parseInt(idAsString);

        ArrayList<Product> productsList = getAllProductsList();
        boolean isRemovedProduct = productsList.removeIf(product -> product.getId() == productId);

        if(isRemovedProduct) {
            if(updateAllProducts(productsList)) {
                return new HttpProductResponse();
            }
        }

        HttpProductResponse response = new HttpProductResponse();
        response.setStatusCode("404");
        return response;
    }
}
