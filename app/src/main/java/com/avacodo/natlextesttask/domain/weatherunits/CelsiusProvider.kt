package com.avacodo.natlextesttask.domain.weatherunits

class CelsiusProvider : BaseUnitsProvider() {
    override fun provideWeatherValue(temperature: Double): String {
        return "${unitsMapper.roundValueToOneDecimal(temperature)}° C"
    }

    override fun provideMaxTempValue(temperature: Double): String {
        return "max: ${unitsMapper.roundValueToOneDecimal(temperature)}° C"
    }

    override fun provideMinTempValue(temperature: Double): String {
        return "min: ${unitsMapper.roundValueToOneDecimal(temperature)}° C"
    }
}