#!/bin/bash
echo "Instalando Control de Gastos en Linux..."
INSTALL_DIR="$HOME/ControlGastos"

# Crear directorio de instalación
mkdir -p "$INSTALL_DIR"

# Copiar contenido de app/
cp -R app/* "$INSTALL_DIR/"

# Dar permisos de ejecución
chmod +x "$INSTALL_DIR/run.sh"

# Crear acceso directo (.desktop file)
cat > ~/.local/share/applications/control-gastos.desktop <<EOL
[Desktop Entry]
Name=Control de Gastos
Exec=$INSTALL_DIR/run.sh
Path=$INSTALL_DIR
Icon=$INSTALL_DIR/icono.png
Terminal=false
Type=Application
Categories=Utility;
EOL

echo "¡Instalación completa! Busca 'Control de Gastos' en tu menú de aplicaciones."