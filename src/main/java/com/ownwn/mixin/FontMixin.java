package com.ownwn.mixin;

import com.ownwn.CustomNames;
import net.minecraft.client.gui.Font;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Font.class)
public class FontMixin {
    @ModifyVariable(at = @At("HEAD"), method = "prepareText(Lnet/minecraft/util/FormattedCharSequence;FFIZZI)Lnet/minecraft/client/gui/Font$PreparedText;", argsOnly = true, name = "text")
    public FormattedCharSequence prepareOrderedText(FormattedCharSequence text) {
        return CustomNames.Companion.replaceName(text);
    }

    @ModifyVariable(at = @At("HEAD"), method = "prepareText(Ljava/lang/String;FFIZI)Lnet/minecraft/client/gui/Font$PreparedText;", argsOnly = true, name = "text")
    public String prepareString(String text) {
        return CustomNames.Companion.replaceName(text);
    }

    @ModifyVariable(at = @At("HEAD"), method = "width(Lnet/minecraft/util/FormattedCharSequence;)I", argsOnly = true, name = "text")
    public FormattedCharSequence getWidth(FormattedCharSequence value) {
        return CustomNames.Companion.replaceName(value);
    }

    @ModifyVariable(at = @At("HEAD"), method = "width(Ljava/lang/String;)I", argsOnly = true, name = "str")
    public String getWidth(String str) {
        return CustomNames.Companion.replaceName(str);
    }
}
