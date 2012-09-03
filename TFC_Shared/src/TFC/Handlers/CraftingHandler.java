package TFC.Handlers;

import TFC.Core.PlayerInfo;
import TFC.Core.PlayerManagerTFC;
import TFC.Core.TFCItems;
import TFC.Core.TFC_Core;
import TFC.Items.ItemTerraFood;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TFCBlocks;
import net.minecraft.src.TerraFirmaCraft;
import cpw.mods.fml.common.ICraftingHandler;

public class CraftingHandler implements ICraftingHandler
{

	@Override
	public void onCrafting(EntityPlayer entityplayer, ItemStack itemstack, IInventory iinventory) 
	{
		int index = 0;

		if(iinventory != null)
		{
			if(itemstack.itemID == TFCBlocks.terraStoneSedBrick.blockID || itemstack.itemID == TFCBlocks.terraStoneIgInBrick.blockID || 
					itemstack.itemID == TFCBlocks.terraStoneIgExBrick.blockID || itemstack.itemID == TFCBlocks.terraStoneMMBrick.blockID)
			{
				HandleItem(entityplayer, iinventory, TFC_Core.Chisels);
			}
			else if(itemstack.itemID == TFCItems.SinglePlank.shiftedIndex)
			{
				HandleItem(entityplayer, iinventory, TFC_Core.Axes);
				HandleItem(entityplayer, iinventory, TFC_Core.Saws);
			}
			else if(itemstack.itemID == Item.bowlEmpty.shiftedIndex || 
					itemstack.getItem() instanceof ItemTerraFood)
			{
				HandleItem(entityplayer, iinventory, TFC_Core.Knives);
			}
			else if(itemstack.itemID == TFCItems.terraWoodSupportItemV.shiftedIndex || itemstack.itemID == TFCItems.terraWoodSupportItemH.shiftedIndex)
			{
				HandleItem(entityplayer, iinventory, TFC_Core.Axes);
			}
			else if(itemstack.itemID == TFCItems.Flux.shiftedIndex)
			{
				HandleItem(entityplayer, iinventory, TFCItems.Hammers);
			}
			else if(itemstack.itemID == TFCItems.LooseRock.shiftedIndex)
			{
				boolean openGui = false;
				for(int i = 0; i < iinventory.getSizeInventory(); i++) 
				{             
					if(iinventory.getStackInSlot(i) == null) 
					{
						continue;
					}
					if(iinventory.getStackInSlot(i).itemID == TFCItems.LooseRock.shiftedIndex)
					{
						if(iinventory.getStackInSlot(i).stackSize == 1)
							iinventory.setInventorySlotContents(i, null);
						else
						{
							ItemStack is = iinventory.getStackInSlot(i); is.stackSize-=1;
							iinventory.setInventorySlotContents(i, is);
						}
						itemstack.stackSize = 1;
						PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer);
						pi.knappingRockType = new ItemStack(TFCItems.FlatRock, 1, itemstack.getItemDamage());
						openGui = true;
					}
				}
				if(openGui)
					entityplayer.openGui(TerraFirmaCraft.instance, 28, entityplayer.worldObj, (int)entityplayer.posX, (int)entityplayer.posY, (int)entityplayer.posZ);

				//itemstack = new ItemStack(TFCItems.FlatRock, 1, itemstack.getItemDamage());
			}
		}
	}
	
	public static void HandleItem(EntityPlayer entityplayer, IInventory iinventory, Item[] Items)
	{
		ItemStack item = null;
		for(int i = 0; i < iinventory.getSizeInventory(); i++) 
		{             
			if(iinventory.getStackInSlot(i) == null) 
			{
				continue;
			}
			for(int j = 0; j < Items.length; j++) 
			{  
				DamageItem(entityplayer,iinventory,i,Items[j].shiftedIndex);
			}
		}
	}
	public static void DamageItem(EntityPlayer entityplayer, IInventory iinventory, int i, int shiftedindex)
	{
		if(iinventory.getStackInSlot(i).itemID == shiftedindex) 
		{
			int index = i;
			ItemStack item = iinventory.getStackInSlot(index).copy();
			if(item != null)
			{
				item.damageItem(1 , entityplayer);
				if (item.getItemDamage() != 0)
				{
					iinventory.setInventorySlotContents(index, item);
					iinventory.getStackInSlot(index).stackSize = iinventory.getStackInSlot(index).stackSize + 1;
					if(iinventory.getStackInSlot(index).stackSize > 2)
						iinventory.getStackInSlot(index).stackSize = 2;
				}
			}
		}
	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack item) {
		// TODO Auto-generated method stub
		
	}

}