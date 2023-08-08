import { MenuItem, Typography } from "@material-tailwind/react";

export interface NavListItemElement {
  key?: number;
  label: string;
  link?: string;
}

export interface NavListItemProps {
  navListItem: NavListItemElement;
}

export default function NavListItem(props: NavListItemProps) {
  const { navListItem } = props;

  return (
    <Typography
      key={navListItem.key}
      as="a"
      href={navListItem.link}
      variant="small"
      color="blue-gray"
      className="font-normal"
    >
      <MenuItem className="flex items-center gap-2 lg:rounded-full">
        {navListItem.label}
      </MenuItem>
    </Typography>
  );
}
