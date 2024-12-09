package ru.bikmag.carsharing.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.bikmag.carsharing.models.Car;

public class CarSpecifications {

    public static Specification<Car> withStatus(String status) {
        return (root, query, criteriaBuilder) ->
                status == null ? null : criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Car> withBrand(String brand) {
        return (root, query, criteriaBuilder) ->
                brand == null ? null : criteriaBuilder.like(root.get("brand"), "%" + brand + "%");
    }

    public static Specification<Car> withModel(String model) {
        return (root, query, criteriaBuilder) ->
                model == null ? null : criteriaBuilder.like(root.get("model"), "%" + model + "%");
    }
}
