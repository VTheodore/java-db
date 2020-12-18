package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dto.picture.PictureSeedRootDto;
import softuni.exam.domain.dto.picture.PictureUrlDto;
import softuni.exam.domain.entity.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.PictureService;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;


import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.util.GlobalConstants.PICTURES_FILE_PATH;


@Service
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;

    private final ValidatorUtil validatorUtil;

    private final ModelMapper modelMapper;

    private final XmlParser xmlParser;

    private boolean areImported = false;

    @PostConstruct
    private void init(){
        this.areImported = this.pictureRepository.count() > 0;
    }

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, ModelMapper modelMapper, ValidatorUtil validatorUtil, XmlParser xmlParser) {
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public String importPictures() throws IOException, JAXBException {
        PictureSeedRootDto pictureSeedRootDto = this.xmlParser.importFromFile(PictureSeedRootDto.class, PICTURES_FILE_PATH);
        StringBuilder sb = new StringBuilder();
        pictureSeedRootDto
                .getPictures()
                .forEach(pictureUrlDto -> {
                    if (this.validatorUtil.isValid(pictureUrlDto)) {
                        if (this.getPictureByUrl(pictureUrlDto.getUrl()) == null) {
                            Picture picture = this.modelMapper.map(pictureUrlDto, Picture.class);
                            this.pictureRepository.save(picture);
                            sb.append(String.format("Successfully imported picture - %s%n", pictureUrlDto.getUrl()));
                        } else {
                            sb.append("Picture is already in DB.").append(System.lineSeparator());
                        }
                    } else {
                        this.validatorUtil
                                .getViolations(pictureUrlDto)
                                .forEach(violation -> sb.append(String.format("Invalid picture! %s%n", violation.getMessage())));
                    }
                });
        areImported = true;
        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return this.areImported;
    }

    @Override
    public String readPicturesXmlFile() throws IOException {
        return Files.readString(Path.of(PICTURES_FILE_PATH));
    }

    @Override
    public Picture getPictureByUrl(String url) {
        return this.pictureRepository.findPictureByUrl(url);
    }

    @Override
    public Picture save(PictureUrlDto pictureUrlDto) {
        if (!this.validatorUtil.isValid(pictureUrlDto)) {
            this.validatorUtil
                    .getViolations(pictureUrlDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
        }
        return this.pictureRepository.save(this.modelMapper.map(pictureUrlDto, Picture.class));
    }
}
