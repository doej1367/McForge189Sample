package me.mcforgesample.wrapper;

import java.util.Objects;

import net.minecraftforge.client.event.sound.PlaySoundEvent;

public class MinecraftSound {
	private String name;
	private float pitch;
	private float volume;
	private float x;
	private float y;
	private float z;
	private long timestamp;

	public MinecraftSound(PlaySoundEvent event) {
		this.name = event.name;
		this.pitch = event.sound.getPitch();
		this.volume = event.sound.getVolume();
		this.x = event.sound.getXPosF();
		this.y = event.sound.getYPosF();
		this.z = event.sound.getZPosF();
		this.timestamp = System.currentTimeMillis();
	}

	public long getAge() {
		return System.currentTimeMillis() - timestamp;
	}

	public String getName() {
		return name;
	}

	public float getPitch() {
		return pitch;
	}

	public float getVolume() {
		return volume;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, pitch, x, y, z);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MinecraftSound other = (MinecraftSound) obj;
		return Objects.equals(name, other.name) && Float.floatToIntBits(pitch) == Float.floatToIntBits(other.pitch)
				&& Float.floatToIntBits(x) == Float.floatToIntBits(other.x)
				&& Float.floatToIntBits(y) == Float.floatToIntBits(other.y)
				&& Float.floatToIntBits(z) == Float.floatToIntBits(other.z);
	}

}
