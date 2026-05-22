package com.supernoob.vanillaopt.villager;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.trading.TradeSet;
import net.minecraft.world.level.block.Block;

import java.util.Objects;

public class MoreVillagers {
    // 1. 定义模组的 ID
    public static final String MOD_ID = "vanillaopt";

    // 2. 声明自定义职业的 ResourceKey
    // 举例：添加一个“音酱” (Oto) 职业
    public static final ResourceKey<VillagerProfession> OTO = createKey();

    // 3. 辅助方法：生成属于模组命名空间的 ResourceKey
    private static ResourceKey<VillagerProfession> createKey() {
        return ResourceKey.create(Registries.VILLAGER_PROFESSION, Objects.requireNonNull(Identifier.tryBuild(MOD_ID, "oto")));
    }

    /**
     * 4. 核心注册方法：模仿原生逻辑，安全地向游戏注册表中塞入你的新职业
     */
    public static void registerProfession(
            Registry<VillagerProfession> registry,
            ResourceKey<VillagerProfession> key,
            ResourceKey<PoiType> jobSite,
            ImmutableSet<Item> requestedItems,
            ImmutableSet<Block> secondaryPoi,
            SoundEvent workSound,
            Int2ObjectMap<ResourceKey<TradeSet>> tradeSets
    ) {
        // 创建原生 VillagerProfession 实例
        VillagerProfession profession = new VillagerProfession(
                // 自动生成语言文件键，如: entity.your_mod_id.villager.beekeeper
                Component.translatable("entity." + key.identifier().getNamespace() + ".villager." + key.identifier().getPath()),
                poiType -> poiType.is(jobSite), // 拥有的工作站点
                poiType -> poiType.is(jobSite), // 可获取的工作站点
                requestedItems,                 // 采集/需要的物品
                secondaryPoi,                   // 次要 POI (如农夫的耕地)
                workSound,                      // 工作时的声音
                tradeSets                       // 各等级的交易列表
        );

        // 安全地注入到 Vanilla 的注册表中
        Registry.register(registry, key, profession);
    }

    /**
     * 5. 初始化入口：在模组初始化（Initialize）阶段调用此方法
     */
    public static void registerAll(Registry<VillagerProfession> registry) {
        // 示例：注册音酱职业
        registerProfession(
                registry,
                OTO,
                ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, Objects.requireNonNull(Identifier.tryBuild(MOD_ID, "oto_poi"))), // 自定义工作方块的 POI
                ImmutableSet.of(), // 可选：添加他会主动捡起的物品
                ImmutableSet.of(), // 可选：次要 POI 方块
                SoundEvents.VILLAGER_WORK_FARMER, // 复用农夫的工作音效（也可换成自定义音效）
                Int2ObjectMap.ofEntries(
                        // 绑定你自定义的交易集合（TradeSets）
                        Int2ObjectMap.entry(1, ResourceKey.create(Registries.TRADE_SET, Objects.requireNonNull(Identifier.tryBuild(MOD_ID, "oto_level_1")))),
                        Int2ObjectMap.entry(2, ResourceKey.create(Registries.TRADE_SET, Objects.requireNonNull(Identifier.tryBuild(MOD_ID, "oto_level_2")))),
                        Int2ObjectMap.entry(3, ResourceKey.create(Registries.TRADE_SET, Objects.requireNonNull(Identifier.tryBuild(MOD_ID, "oto_level_3")))),
                        Int2ObjectMap.entry(4, ResourceKey.create(Registries.TRADE_SET, Objects.requireNonNull(Identifier.tryBuild(MOD_ID, "oto_level_4")))),
                        Int2ObjectMap.entry(5, ResourceKey.create(Registries.TRADE_SET, Objects.requireNonNull(Identifier.tryBuild(MOD_ID, "oto_level_5"))))
                )
        );
    }
}
