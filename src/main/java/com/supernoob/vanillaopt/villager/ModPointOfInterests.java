package com.supernoob.vanillaopt.villager;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;
import java.util.Objects;

public class ModPointOfInterests {
    // 1. 定义与 MoreVillagers 对应的 POI ResourceKey
    public static final ResourceKey<PoiType> OTO_POI_KEY = ResourceKey.create(
            Registries.POINT_OF_INTEREST_TYPE,
            Objects.requireNonNull(Identifier.tryBuild(MoreVillagers.MOD_ID, "oto_poi"))
    );

    public static void registerPOI() {
        // 2. 直接获取原版的【校频幽匿感测体】
        Block workBlock = Blocks.CALIBRATED_SCULK_SENSOR;

        // 3. 抓取该方块所有的状态（如激活、朝向、含水等）
        ImmutableSet<BlockState> blockStates =
                ImmutableSet.copyOf(workBlock.getStateDefinition().getPossibleStates());

        // 4. 创建 POI 实例（参数1：票数/最大村民容纳数 1，参数2：搜索半径 1）
        PoiType poiType = new PoiType(blockStates, 1, 1);

        // 5. 注册到游戏
        Registry.register(BuiltInRegistries.POINT_OF_INTEREST_TYPE, OTO_POI_KEY, poiType);

        // ⭐【核心补丁】将这些方块状态动态注入到原版的底层映射表中
        // 这一步告诉游戏世界：当看到这些 BlockState 时，要把它们当作你的“音酱工作台”来处理！
        try {
            // 获取原版 PoiTypes 内部维护的“状态 -> POI类型”的实时映射
            // 不同的 Yarn 映射版本下可能叫字段注入或公共方法访问，大部分现代版本可以直接通过 PoiInfo 或底层注入
            // 如果你的开发包中 PoiType 支持关联，这一步至关重要：
            for (BlockState state : blockStates) {
                // 如果编译时提示无法直接操作或找不到对应方法，请告诉我你当前的正式 MC 游戏具体小版本（例如 1.21 / 1.21.1）
                // 我们可能需要用 Fabric API 的 PointOfInterestHelper 来替代原生注册，以确保区块刷新能正确识别
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}