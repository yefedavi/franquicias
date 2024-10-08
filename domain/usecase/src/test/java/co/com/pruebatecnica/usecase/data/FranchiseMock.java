package co.com.pruebatecnica.usecase.data;

import co.com.pruebatecnica.model.BranchOffice;
import co.com.pruebatecnica.model.Franchise;
import co.com.pruebatecnica.model.Product;

import java.util.Arrays;
import java.util.Collections;

public class FranchiseMock {
    public static Franchise createFranchise() {
        Product product = new Product("PRODUCTO_1",5,"SUCURSAL_1");
        Product product2 = new Product("PRODUCTO_2",7,"SUCURSAL_1");
        BranchOffice branchOffice = new BranchOffice("FRANQUICIA_3","SUCURSAL_1", Arrays.asList(product,product2));
        return new Franchise("FRANQUICIA_3",Collections.singletonList(branchOffice));
    }
}
