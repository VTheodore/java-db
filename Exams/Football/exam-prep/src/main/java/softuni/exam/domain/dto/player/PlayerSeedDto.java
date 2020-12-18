package softuni.exam.domain.dto.player;

import com.google.gson.annotations.Expose;
import softuni.exam.domain.dto.picture.PictureUrlDto;
import softuni.exam.domain.dto.team.TeamNameAndPictureDto;
import softuni.exam.domain.entity.Position;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class PlayerSeedDto {
    @NotNull(message = "Player name cannot be null.")
    @Expose
    private String firstName;

    @NotNull(message = "Player name cannot be null.")
    @Expose
    private String lastName;

    @Min(value = 0, message = "Player number must be between 0 and 99.")
    @Max(value = 99, message = "Player number must be between 0 and 99.")
    @NotNull(message = "Player number cannot be null.")
    @Expose
    private byte number;

    @NotNull(message = "Player salary cannot be null")
    @PositiveOrZero(message = "Player salary cannot be negative.")
    @Expose
    private BigDecimal salary;

    @NotNull(message = "Player position cannot be null.")
    @Expose
    private Position position;

    @Expose
    private PictureUrlDto picture;

    @Expose
    private TeamNameAndPictureDto team;

    public PlayerSeedDto() {
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

    public byte getNumber() {
        return this.number;
    }

    public void setNumber(byte number) {
        this.number = number;
    }

    public BigDecimal getSalary() {
        return this.salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public PictureUrlDto getPicture() {
        return this.picture;
    }

    public void setPicture(PictureUrlDto picture) {
        this.picture = picture;
    }

    public TeamNameAndPictureDto getTeam() {
        return this.team;
    }

    public void setTeam(TeamNameAndPictureDto team) {
        this.team = team;
    }
}
