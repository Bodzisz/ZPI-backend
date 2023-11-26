package zpi.dto;

public record NewAttractionDto(String district, String cityName, String postalCode, String attractionType, String title,
                               String description, String picture, Float xCoordinate, Float yCoordinate) {
}
