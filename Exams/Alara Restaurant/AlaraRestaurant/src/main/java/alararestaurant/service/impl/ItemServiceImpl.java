package alararestaurant.service.impl;

import alararestaurant.domain.dtos.ItemSeedDto;
import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.ItemRepository;
import alararestaurant.service.CategoryService;
import alararestaurant.service.ItemService;
import alararestaurant.util.FileUtil;
import alararestaurant.util.GlobalConstants;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static alararestaurant.util.GlobalConstants.*;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    private final Gson gson;

    private final CategoryService categoryService;

    private final ModelMapper modelMapper;

    private final FileUtil fileUtil;

    private final ValidationUtil validationUtil;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, Gson gson, CategoryService categoryService, ModelMapper modelMapper, FileUtil fileUtil, ValidationUtil validationUtil) {
        this.itemRepository = itemRepository;
        this.gson = gson;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean itemsAreImported() {
        return this.itemRepository.count() > 0;
    }

    @Override
    public String readItemsJsonFile() {
        return this.fileUtil.readFile(ITEMS_FILE_PATH);
    }

    @Override
    public String importItems(String items) {
        ItemSeedDto[] itemSeedDtos = this.gson.fromJson(items, ItemSeedDto[].class);
        StringBuilder itemsOutput = new StringBuilder();

        Arrays.stream(itemSeedDtos)
                .forEach(itemSeedDto -> {
                    if (this.validationUtil.isValid(itemSeedDto)) {
                        Category category = this.categoryService.getCategoryByName(itemSeedDto.getCategory());

                        if (category == null) {
                            category = this.categoryService.save(itemSeedDto.getCategory());
                        }

                        Item item = this.modelMapper.map(itemSeedDto, Item.class);
                        item.setCategory(category);

                        try {
                            this.itemRepository.save(item);
                            itemsOutput.append(String.format(SUCCESSFULLY_IMPORTED, itemSeedDto.getName()));
                        }catch (Exception e) {
                            itemsOutput.append(ERROR_MESSAGE);
                        }
                    } else {
                        itemsOutput.append(ERROR_MESSAGE);
                    }

                    itemsOutput.append(System.lineSeparator());
                });
        return itemsOutput.toString();
    }

    @Override
    public Item getItemByName(String name) {
        return this.itemRepository.findItemByName(name);
    }
}
