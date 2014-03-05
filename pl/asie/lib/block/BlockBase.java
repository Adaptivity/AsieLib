package pl.asie.lib.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public abstract class BlockBase extends BlockContainer {
	private final Object parent;
	private int gui = -1;
	
	public BlockBase(int id, Material material, Object parent) {
		super(id, material);
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setHardness(1.5F);
		this.parent = parent;
	}
	
	public Object getOwner() { return parent; }
	
	public void setGuiID(int gui) { if(gui >= 0) this.gui = gui; }
	public boolean hasGui() { return (gui >= 0); }
	public int getGuiID() { return gui; }
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		if(!world.isRemote)
			player.openGui(this.parent, this.gui, world, x, y, z);
		return true;
	}
	
	// Simple textures
	
	private String iconName = null;
	private Icon icon = null;
	
	public void setIconName(String name) {
		iconName = name;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return icon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		if(iconName != null) icon = reg.registerIcon(iconName);
	}
	
	// Block destroy unified handler and whatnot.
	public void onBlockDestroyed(World world, int x, int y, int z, int meta) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if(tileEntity != null) {
			tileEntity.invalidate();
		}
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta) {
		super.onBlockDestroyedByPlayer(world, x, y, z, meta);
		this.onBlockDestroyed(world, x, y, z, meta);
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion) {
		super.onBlockDestroyedByExplosion(world, x, y, z, explosion);
		this.onBlockDestroyed(world, x, y, z, 0);
	}

    @Override
	public void breakBlock(World world, int x, int y, int z, int id, int meta) {
    	this.onBlockDestroyed(world, x, y, z, meta);
    	super.breakBlock(world, x, y, z, id, meta);
    }
}
