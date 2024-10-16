import { createTheme, MantineProvider } from "@mantine/core";
import { Notifications } from "@mantine/notifications";
import TodoOverview from "./components/TodoOverview.tsx";

import "@mantine/core/styles.css";
import "@mantine/notifications/styles.css";

const App = () => {
  const theme = createTheme({});

  return (
    <MantineProvider theme={theme}>
      <Notifications />
      <TodoOverview />
    </MantineProvider>
  );
};

export default App;
