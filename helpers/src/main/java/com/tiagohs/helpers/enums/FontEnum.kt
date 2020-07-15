package com.tiagohs.helpers.enums

import android.content.res.AssetManager
import android.graphics.Typeface

enum class FontEnum(
        val fontName: String
) {
    PROXIMA_NOVA_REGULAR("proxima_nova_regular.otf"),
    PROXIMA_NOVA_BOLD("proxima_nova_bold.otf"),
    PROXIMA_NOVA_BOLD_ITALIC("proxima_nova_bold_italic.otf"),
    PROXIMA_NOVA_REGULAR_ITALIC("proxima_nova_regular_italic.otf"),
    OPEN_SANS_BOLD("opensans_bold.ttf"),
    OPEN_SANS_ITALIC("opensans_italic.ttf"),
    OPEN_SANS_LIGHT("opensans_light.ttf"),
    OPEN_SANS_REGULAR("opensans_regular.ttf"),
    OPEN_SANS_CONDENSED_BOLD("opensanssondensed_bold.ttf"),
    OPEN_SANS_CONDENSED_LIGHT("opensanssondensed_light.ttf"),
    OSWALD_BOLD("oswald_bold.ttf"),
    OSWALD_LIGHT("oswald_light.ttf"),
    OSWALD_REGULAR("oswald_regular.ttf"),
    ROBOTO_BOLD("roboto_bold.ttf"),
    ROBOTO_LIGHT("roboto_light.ttf"),
    ROBOTO_ITALIC("roboto_italic.ttf"),
    ROBOTO_MEDIUM("roboto_medium.ttf"),
    ROBOTO_REGULAR("roboto_regular.ttf"),
    ROBOTO_THIN("roboto_thin.ttf"),
    ROBOTO_MONO_BOLD("robotomono_bold.ttf"),
    ROBOTO_MONO_ITALIC("robotomono_italic.ttf"),
    ROBOTO_MONO_LIGHT("robotomono_light.ttf"),
    ROBOTO_MONO_MEDIUM("robotomono_medium.ttf"),
    ROBOTO_MONO_REGULAR("robotomono_regular.ttf"),
    ROBOTO_MONO_THIN("robotomono_thin.ttf"),
    BIG_SHOULDERS_DISPLAY_BOLD("bigshouldersdisplay_bold.ttf"),
    BIG_SHOULDERS_DISPLAY_LIGHT("bigshouldersdisplay_light.ttf"),
    BIG_SHOULDERS_DISPLAY_MEDIUM("bigshouldersdisplay_medium.ttf"),
    BIG_SHOULDERS_DISPLAY_REGULAR("bigshouldersdisplay_regular.ttf"),
    BIG_SHOULDERS_DISPLAY_THIN("bigshouldersdisplay_thin.ttf"),
    HEPTASLAB_BOLD("heptaslab_bold.ttf"),
    HEPTASLAB_LIGHT("heptaslab_light.ttf"),
    HEPTASLAB_MEDIUM("heptaslab_medium.ttf"),
    HEPTASLAB_REGULAR("heptaslab_regular.ttf"),
    HEPTASLAB_THIN("heptaslab_thin.ttf"),
    BILLIONAIRE_MEDIUM_GRUNGE("billionairemediumgrunge.ttf"),
    JOMOLHARI_REGULAR("jomolhari_regular.ttf"),
    MONTSERRAT_BOLD("montserrat_bold.ttf"),
    MONTSERRAT_ITALIC("montserrat_italic.ttf"),
    MONTSERRAT_LIGHT("montserrat_light.ttf"),
    MONTSERRAT_MEDIUM("montserrat_medium.ttf"),
    MONTSERRAT_REGULAR("montserrat_regular.ttf"),
    MONTSERRAT_THIN("montserrat_thin.ttf");

    val fontWithPath: String
        get() = String.format("fonts/%s.otf", this.fontName)

    fun getTypeface(asset: AssetManager?): Typeface {
        return Typeface.createFromAsset(asset, this.fontWithPath)
    }
}