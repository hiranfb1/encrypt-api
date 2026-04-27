package fernandakipper.com.encrypt_api.dto;

public record OperationDTO(
        String userDocument,
        String creditCardToken,
        Long operationValue) {
}