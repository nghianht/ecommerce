package com.example.ecommercedemo.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CartsDto {
    private Long cartId;
    private Integer cartTotal;
    private Long userId;
    private List<CartsItemsDto> cartsItemsDtoList;

}
