/*
 * This file is part of ComputerCraft - http://www.computercraft.info
 * Copyright Daniel Ratcliffe, 2011-2020. Do not distribute without permission.
 * Send enquiries to dratcliffe@gmail.com
 */
package dan200.computercraft.shared.computer.inventory;

import dan200.computercraft.ComputerCraft;
import dan200.computercraft.shared.ComputerCraftRegistry;
import dan200.computercraft.shared.computer.blocks.TileCommandComputer;
import dan200.computercraft.shared.computer.core.ComputerFamily;
import dan200.computercraft.shared.computer.core.IContainerComputer;
import dan200.computercraft.shared.computer.core.ServerComputer;
import dan200.computercraft.shared.network.container.ViewComputerContainerData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;

import javax.annotation.Nonnull;

public class ContainerViewComputer extends ContainerComputerBase implements IContainerComputer
{
    private final int width;
    private final int height;

    public ContainerViewComputer( int id, ServerComputer computer )
    {
        super( ComputerCraftRegistry.ModContainers.VIEW_COMPUTER, id, player -> canInteractWith( computer, player ), computer, computer.getFamily() );
        this.width = this.height = 0;
    }
    public ContainerViewComputer(int id, PlayerInventory player, PacketByteBuf packetByteBuf)
    {
        super( ComputerCraftRegistry.ModContainers.VIEW_COMPUTER, id, player, packetByteBuf );
        ViewComputerContainerData data = new ViewComputerContainerData((PacketByteBuf) packetByteBuf.copy());
        this.width = data.getWidth();
        this.height = data.getHeight();
    }

    private static boolean canInteractWith( @Nonnull ServerComputer computer, @Nonnull PlayerEntity player )
    {
        // If this computer no longer exists then discard it.
        if( ComputerCraft.serverComputerRegistry.get( computer.getInstanceID() ) != computer )
        {
            return false;
        }

        // If we're a command computer then ensure we're in creative
        if( computer.getFamily() == ComputerFamily.COMMAND && !TileCommandComputer.isUsable( player ) )
        {
            return false;
        }

        return true;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
}
