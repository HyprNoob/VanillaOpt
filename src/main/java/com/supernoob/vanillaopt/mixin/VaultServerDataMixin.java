package com.supernoob.vanillaopt.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.vault.VaultServerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.UUID;

@Mixin(VaultServerData.class)
public class VaultServerDataMixin {
	@Inject(
			method = "addToRewardedPlayers",
			at = @At("HEAD"),
			cancellable = true
	)
	private void disableRewardTracking(Player player, CallbackInfo ci) {
		ci.cancel();
	}
}