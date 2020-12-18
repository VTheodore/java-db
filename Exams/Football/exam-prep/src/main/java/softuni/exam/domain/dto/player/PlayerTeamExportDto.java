package softuni.exam.domain.dto.player;

import softuni.exam.domain.entity.Position;

public class PlayerTeamExportDto {
    private String firstName;

    private String lastName;

    private Position position;

    private byte number;

    public PlayerTeamExportDto() {
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public byte getNumber() {
        return this.number;
    }

    public void setNumber(byte number) {
        this.number = number;
    }
}
