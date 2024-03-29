package Enumerations;

/**
 * Data representation of a Gem to be associated with the Tile.
 */
public enum Gem {
  alexandrite_pear_shape, alexandrite, almandine_garnet, amethyst, ametrine, ammolite, apatite,
  aplite, apricot_square_radiant, aquamarine, australian_marquise, aventurine, azurite, beryl,
  black_obsidian, black_onyx, black_spinel_cushion, blue_ceylon_sapphire, blue_cushion, blue_pear_shape,
  blue_spinel_heart, bulls_eye, carnelian, chrome_diopside, chrysoberyl_cushion, chrysolite,
  citrine_checkerboard, citrine, clinohumite, color_change_oval, cordierite, diamond, dumortierite,
  emerald, fancy_spinel_marquise, garnet, golden_diamond_cut,
  goldstone, grandidierite, gray_agate, green_aventurine, green_beryl_antique, green_beryl,
  green_princess_cut, grossular_garnet, hackmanite, heliotrope, hematite, iolite_emerald_cut, jasper,
  jaspilite, kunzite_oval, kunzite, labradorite, lapis_lazuli, lemon_quartz_briolette, magnesite,
  mexican_opal, moonstone, morganite_oval, moss_agate, orange_radiant, padparadscha_oval,
  padparadscha_sapphire, peridot, pink_emerald_cut, pink_opal, pink_round, pink_spinel_cushion,
  prasiolite, prehnite, purple_cabochon, purple_oval, purple_spinel_trillion, purple_square_cushion,
  raw_beryl, raw_citrine, red_diamond, red_spinel_square_emerald_cut, rhodonite, rock_quartz, rose_quartz,
  ruby_diamond_profile, ruby, sphalerite, spinel, star_cabochon, stilbite, sunstone, super_seven,
  tanzanite_trillion, tigers_eye, tourmaline_laser_cut, tourmaline, unakite, white_square, yellow_baguette,
  yellow_beryl_oval, yellow_heart, yellow_jasper, zircon, zoisite;

  public static Gem getGemOf(String value) {
    value = value.replace("-", "_");
    return Gem.valueOf(value);
  }

  @Override
  public String toString() {
    return super.toString().replace("_", "-");
  }

}