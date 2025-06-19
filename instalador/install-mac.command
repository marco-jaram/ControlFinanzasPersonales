#!/bin/bash
echo "Instalando Control de Gastos en macOS..."
INSTALL_DIR="$HOME/Applications/ControlGastos"

# Crear directorio
mkdir -p "$INSTALL_DIR"

# Copiar archivos
cp -R app/* "$INSTALL_DIR/"

# Crear aplicación ejecutable
cat > "$INSTALL_DIR/Control Gastos.command" <<EOL
#!/bin/bash
cd "$INSTALL_DIR"
java -jar control-gastos.jar
EOL

chmod +x "$INSTALL_DIR/Control Gastos.command"

# Crear acceso directo en el escritorio
ln -s "$INSTALL_DIR/Control Gastos.command" "$HOME/Desktop/Control Gastos"

echo "¡Instalación completa! Se ha creado un icono en tu escritorio."