package be.cegeka.ng2boot.smurf;

import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class SmurfAssemblerTest {

    @Test
    public void canAssembleASmurfRFromASmurfEntity() throws Exception {
        LocalDate now = LocalDate.now();
        Smurf smurf = SmurfTestBuilder.aDefaultSmurf().withCreationDate(now).build();
        SmurfR smurfR = SmurfAssembler.toRepresentation(smurf);

        SmurfR expectedSmurfR = SmurfRTestBuilder.aSmurfR()
                .withName(SmurfTestBuilder.NAME)
                .withCreationDate(now)
                .build();

        assertThat(smurfR).isEqualTo(expectedSmurfR);
    }

    @Test
    public void canAssembleASmurfEntityFromASmurfRepresentation() throws Exception {
        LocalDate now = LocalDate.now();
        SmurfR smurfR = SmurfRTestBuilder.aDefaultSmurfR().withCreationDate(now).build();
        Smurf smurf = SmurfAssembler.toDomain(smurfR);

        Smurf expectedSmurf = SmurfTestBuilder.aSmurf()
                .withName(SmurfTestBuilder.NAME)
                .withCreationDate(now)
                .build();

        assertThat(smurf).isEqualTo(expectedSmurf);
    }
}