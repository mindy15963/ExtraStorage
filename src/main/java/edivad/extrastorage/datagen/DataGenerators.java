package edivad.extrastorage.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        if(event.includeServer()) {
            TagGenerator.BlockTags blockTagsProvider = new TagGenerator.BlockTags(generator, existingFileHelper);
            generator.addProvider(blockTagsProvider);
            generator.addProvider(new TagGenerator.ItemTags(generator, blockTagsProvider, existingFileHelper));
            generator.addProvider(new Recipes(generator));
            generator.addProvider(new Lang(generator));
            generator.addProvider(new LootTableGenerator(generator));
        }
        if(event.includeClient()) {
            generator.addProvider(new BlockStates(generator, existingFileHelper));
            generator.addProvider(new Items(generator, existingFileHelper));
        }
    }
}
