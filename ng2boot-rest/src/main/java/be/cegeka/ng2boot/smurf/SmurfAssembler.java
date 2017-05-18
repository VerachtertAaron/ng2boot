package be.cegeka.ng2boot.smurf;

import static be.cegeka.ng2boot.smurf.Smurf.SmurfBuilder.aSmurf;

public class SmurfAssembler {
    public static SmurfR toRepresentation(Smurf smurf) {
        return new SmurfR(smurf.getId(), smurf.getName(), smurf.getCreationDate());
    }
    public static Smurf toDomain(SmurfR smurf) {
        return aSmurf()
                .withName(smurf.getName())
                .withCreationDate(smurf.getCreationDate())
                .build();
    }
}
