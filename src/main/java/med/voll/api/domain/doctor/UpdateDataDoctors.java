package med.voll.api.domain.doctor;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.address.AddressData;

public record UpdateDataDoctors(@NotNull Long id, AddressData address, String DNI, String name, String phone ) {}