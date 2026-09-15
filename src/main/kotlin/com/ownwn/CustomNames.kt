package com.ownwn

import com.ownwn.config.Config
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.util.FormattedCharSequence
import kotlin.collections.joinToString


class CustomNames {
    companion object {
        private val config = Config.instance()


        private fun FormattedCharSequence.contains(string: String): Boolean {
            return TextUtils.openOrderedText(this).joinToString("") { it.string }.contains(string)
        }

        /** returns the players username, or null if not enabled */
        private fun getUsername(): String? {
            val username = Minecraft.getInstance().player?.name?.string

            val customNameEnabled = config.customNameToggle && config.customName.isNotEmpty()

            // if player's name is same as username then return to avoid infinite .replace loop
            if (!customNameEnabled || config.customName == username) return null
            return username
        }

        /** @return An edited `OrderedText` with the player's custom name and rank*/
        fun replaceName(string: String): String {
            val username = getUsername() ?: return string
            val customRankEnabled = config.customRankToggle && config.customRank.isNotEmpty()

            var newString = string

            // loop in case username appears in multiple places
            // weird things can happen in the YACL editor with this, have a failsafe counter just in case
            var repetitionCounter = 0
            while (++repetitionCounter < 10 && string.contains(username)) {

                // replace rank
                if (customRankEnabled && newString.contains(config.manualRankSelector + " " + username)) {
                    newString = newString.replace(config.manualRankSelector, config.customRank)
                }

                // replace name
                newString = newString.replace(username, config.customName)
            }

            return newString
        }

        /** @return An edited `OrderedText` with the player's custom name and rank*/
        fun replaceName(text: FormattedCharSequence): FormattedCharSequence {
            val username = getUsername() ?: return text
            val customRankEnabled = config.customRankToggle && config.customRank.isNotEmpty()

            val originalTextArray = TextUtils.openOrderedText(text)
            if (!originalTextArray.joinToString("") { it.string }.contains(username)) return text


            var currentText = text

            // loop in case username appears in multiple places
            // weird things can happen in the YACL editor with this, have a failsafe counter just in case
            var repetitionCounter = 0
            while (++repetitionCounter < 10 && currentText.contains(username)) {

                // replace rank
                if (customRankEnabled && currentText.contains(config.manualRankSelector + " " + username)) {
                    currentText = TextUtils.replaceOrderedText(currentText,
                        config.manualRankSelector,
                        getCustomText(config.customRank, config.customRankType, false)
                    )
                }

                // replace name
                currentText = TextUtils.replaceOrderedText(currentText, username,
                    getCustomText(config.customName, config.customNameType, true)
                )
            }

            return currentText
        }

        /** @param oldText The text to be edited
         * @param chromaType Which type of chroma the user has selected from config
         * @param isCustomName Whether to use the custom name static colour, or the custom rank static colour*/
        private fun getCustomText(oldText: MutableComponent, chromaType: Config.ChromaType, isCustomName: Boolean): Component {
            // Replace only the text; do not add or modify any colour/style
            if (isCustomName && !config.staticNameModifyColor) {
                return Component.literal(oldText.string)
            }

            return when (chromaType) {
                Config.ChromaType.FANCY_CHROMA -> TextUtils.dynamicSpectrum(oldText)
                // get the correct static colour for rank/name
                Config.ChromaType.STATIC_COLOUR -> oldText.withColor(
                    if (isCustomName) config.staticNameColour.rgb else config.staticRankColour.rgb
                )
                Config.ChromaType.PLAIN_CHROMA -> oldText.withColor(TextUtils.colourSpectrum())
            }
        }
        /** overload of [getCustomText]*/
        private fun getCustomText(oldText: String, chromaType: Config.ChromaType, isCustomName: Boolean): Component {
            return getCustomText(Component.literal(oldText), chromaType, isCustomName)
        }
    }


}


