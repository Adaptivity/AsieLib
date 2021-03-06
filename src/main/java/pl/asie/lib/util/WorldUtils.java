package pl.asie.lib.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import pl.asie.lib.AsieLibMod;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class WorldUtils {
	public static TileEntity getTileEntity(int dimensionId, int x, int y, int z) {
		World world = AsieLibMod.proxy.getWorld(dimensionId);
		if(world == null) return null;
		return world.getBlockTileEntity(x, y, z);
	}
	
	public static TileEntity getTileEntityServer(int dimensionId, int x, int y, int z) {
		World world = MinecraftServer.getServer().worldServerForDimension(dimensionId);
		if(world == null) return null;
		return world.getBlockTileEntity(x, y, z);
	}
	
	public static boolean equalLocation(TileEntity a, TileEntity b) {
		if(a == null || b == null || a.worldObj == null || b.worldObj == null) return false;
		return a.xCoord == b.xCoord && a.yCoord == b.yCoord && a.zCoord == b.zCoord
				&& a.worldObj.provider.dimensionId == b.worldObj.provider.dimensionId;
	}
	public static Block getBlock(World world, int x, int y, int z) {
		return Block.blocksList[world.getBlockId(x, y, z)];
	}
	
	public static int getCurrentClientDimension() {
		return AsieLibMod.proxy.getCurrentClientDimension();
	}
}
