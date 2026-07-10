package com.ownwn.config

import com.ownwn.ClientCustomName
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler
import dev.isxander.yacl3.config.v2.api.SerialEntry
import dev.isxander.yacl3.config.v2.api.autogen.*
import dev.isxander.yacl3.config.v2.api.autogen.Boolean
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.gui.screens.Screen
import net.minecraft.resources.Identifier
import java.awt.Color

class Config {
    companion object {
        private val HANDLER: ConfigClassHandler<Config> = ConfigClassHandler.createBuilder(Config::class.java)
            .id(Identifier.fromNamespaceAndPath(ClientCustomName.MODID, "config"))
            .serializer { config ->
                GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().configDir.resolve( ClientCustomName.MODID + ".json5"))
                    .setJson5(true)
                    .build()
            }
            .build()


        fun createScreen(parent: Screen?): Screen {
            return HANDLER.generateGui().generateScreen(parent)
        }

        fun instance(): Config {
            return HANDLER.instance()
        }

        fun load() {
            HANDLER.load()
        }
    }



    @SerialEntry
    @AutoGen(category = "custom_name")
    @MasterTickBox(value = ["customName", "customNameType", "staticNameColour", "manualRankSelector", "customRankToggle", "customRank", "customRankType", "staticRankColour"])
    var customNameToggle: kotlin.Boolean = true

    @SerialEntry
    @AutoGen(category = "custom_name")
    @StringField
    var customName: String = "custom name"

    @SerialEntry
    @AutoGen(category = "custom_name")
    @EnumCycler
    var customNameType: ChromaType = ChromaType.FANCY_CHROMA

    @SerialEntry
    @AutoGen(category = "custom_name")
    @ColorField
    var staticNameColour: Color = Color(10898943)



    @SerialEntry
    @AutoGen(category = "custom_rank")
    @Dropdown(values = ["None", "[VIP]", "[VIP+]", "[MVP]", "[MVP+]", "[MVP++]"])
    var manualRankSelector: String = "None"

    @SerialEntry
    @AutoGen(category = "custom_rank")
    @MasterTickBox(value = ["customRank", "customRankType", "staticRankColour", "manualRankSelector"])
    var customRankToggle: kotlin.Boolean = true

    @SerialEntry
    @AutoGen(category = "custom_rank")
    @StringField
    var customRank: String = "custom rank"

    @SerialEntry
    @AutoGen(category = "custom_rank")
    @EnumCycler
    var customRankType: ChromaType = ChromaType.FANCY_CHROMA

    @SerialEntry
    @AutoGen(category = "custom_rank")
    @ColorField
    var staticRankColour: Color = Color(2549248)




    @SerialEntry
    @AutoGen(category = "chroma")
    @DoubleSlider(min = 0.1, max = 10.0, format = "%.1f", step = 0.1)
    var chromaSpeed: Double = 5.0

    @SerialEntry
    @AutoGen(category = "chroma")
    @DoubleSlider(min = 0.1, max = 10.0, step = 0.1, format = "%.1f")
    var chromaScale: Double = 5.0

    @SerialEntry
    @AutoGen(category = "chroma")
    @FloatSlider(min = 0.1f, max = 1f, step = 0.01f, format = "%.2f")
    var chromaBrightness: Float = 0.9f

    @SerialEntry
    @AutoGen(category = "chroma")
    @FloatSlider(min = 0.1f, max = 1f, step = 0.01f, format = "%.2f")
    var chromaSaturation: Float = 0.8f

    @SerialEntry
    @AutoGen(category = "chroma")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    var reverseChromaDirection: kotlin.Boolean = false

    enum class ChromaType {
        PLAIN_CHROMA,
        FANCY_CHROMA,
        STATIC_COLOUR
    }

}